package dev.yudin.core;

import static org.junit.jupiter.api.Assertions.*;

import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

class DataGeneratorTest {

	public static final String REGEX = "[A-Za-z]+-[0-9]+";

	@Test
	void generateGroups_ShouldGenerateGroupsNames_WhenInputIsAmountGroups() {

		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(reader);

		var groups = dataGenerator.generateGroups(10);

		assertTrue(groups.stream().allMatch(group -> group.matches(REGEX)));
	}

	@Test
	void name() {
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(reader);

		var actual = dataGenerator.generateStudents(200);

//		actual.stream()
//				.forEach(System.out::println);

		assertEquals(200, actual.size());
	}

	@Test
	void name2() {
		Reader reader = new FileReader();
		var studentsList = reader.read("src/main/resources/students.txt");

		var test = studentsList.stream()
				.map(value -> value.split(" "))
				.collect(Collectors.toList());




	}
}