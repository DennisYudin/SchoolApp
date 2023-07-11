package dev.yudin.core;

import static org.junit.jupiter.api.Assertions.*;

import dev.yudin.connection.FileReaderTesting;
import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;
import org.junit.jupiter.api.Test;

class DataGeneratorTest {

	public static final String REGEX = "[A-Za-z]+-[0-9]+";

	@Test
	void generateGroups_ShouldGenerateGroupsNames_WhenInputIsAmountGroups() {

		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(reader);

		var groups = dataGenerator.generateGroups(10);

		int expected = 10;
		int actual = groups.size();

		assertEquals(expected, actual);
		assertTrue(groups.stream().allMatch(group -> group.matches(REGEX)));
	}

	@Test
	void generateStudents_ShouldGenerateStudents_WhenInputIsAmountStudents() {
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(reader);

		var actual = dataGenerator.generateStudents(200);

		int expected = 200;
		int actual1 = actual.size();

		assertEquals(expected, actual1);
	}
}