package dev.yudin.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FileReaderTest {

	@Test
	void read_ShouldReadFile_WhenInputIsPathToTheFile() {

		Reader reader = new FileReader();

		var actual = reader.read("src/main/resources/students");

		assertEquals(20, actual.size());
		assertEquals("Dick Van Dyke", actual.get(actual.size() - 1));
	}

	@Test
	void name() {
		Reader reader = new FileReader();

		var actual = reader.getValue("db.driver");

		System.out.println(actual);
	}
}
