package dev.yudin.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FileReaderTest {

	@Test
	void read_ShouldReadFile_WhenInputIsPathToTheFile() {

		Reader reader = new FileReader();

		var actual = reader.read("src/test/resources/students.txt");

		assertEquals(20, actual.size());
		assertEquals("Dick VanDyke", actual.get(actual.size() - 1));
	}

	@Test
	void getValue_ShouldReadValueFromApplicationFile_WhenInputIsValueName() {

		Reader reader = new FileReader();

		var actual = reader.getValue("db.driver");
		String expected = "org.h2.Driver";

		assertEquals(expected, actual);
	}
}
