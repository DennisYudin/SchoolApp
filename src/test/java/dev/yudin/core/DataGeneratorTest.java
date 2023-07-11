package dev.yudin.core;

import static org.junit.jupiter.api.Assertions.*;

import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;
import org.junit.jupiter.api.Test;

import java.util.Random;

class DataGeneratorTest {

	public static final String REGEX = "[A-Za-z]+-[0-9]+";

	@Test
	void generateGroups_ShouldGenerateGroupsNames_WhenInputIsAmountGroups() {

		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);

		var groups = dataGenerator.generateGroups(10);

		int expected = 10;
		int actual = groups.size();

		assertEquals(expected, actual);
		assertTrue(groups.stream().allMatch(group -> group.matches(REGEX)));
	}

	@Test
	void generateStudents_ShouldGenerateStudents_WhenInputIsAmountStudents() {
		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);

		var actual = dataGenerator.generateStudents(200);

		int expected = 200;
		int actual1 = actual.size();

		assertEquals(expected, actual1);
	}

	@Test
	void getCourses_ShouldReturnListOfCoursesWithDescriptions_WhenCallMethod() {
		Random random = new Random();
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(random, reader);

		var actual = dataGenerator.getCourses();

		int expectedSize = 10;
		int actualSize = actual.size();

		assertEquals(expectedSize, actualSize);
	}
}