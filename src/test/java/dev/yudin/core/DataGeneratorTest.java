package dev.yudin.core;

import static org.junit.jupiter.api.Assertions.*;

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

		assertTrue(groups.stream().allMatch(group -> group.matches(REGEX)));
	}

	@Test
	void name() {
		Reader reader = new FileReader();
		DataGenerator dataGenerator = new DataGenerator(reader);

		var actual = dataGenerator.generateStudents();

		System.out.println(actual);
	}
}