package dev.yudin.core;

import static org.junit.jupiter.api.Assertions.*;

import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;
import org.junit.jupiter.api.Test;

import java.util.Random;

class DataDistributorTest {

	@Test
	void name() {

		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);



		DataDistributor distributor = new DataDistributor();

		var actual = distributor.assignStudentsIntoCourses(dataGenerator.generateStudents(200), dataGenerator.getCourses());

		actual.forEach(System.out::println);
	}
}