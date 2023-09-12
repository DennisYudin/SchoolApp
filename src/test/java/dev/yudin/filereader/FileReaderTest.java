package dev.yudin.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FileReaderTest {

	@Test
	void read_ShouldReadFile_WhenInputIsPathToTheFile() {

		Reader reader = new FileReader();

		var surnames = reader.read("students_surnames.txt");

		int expected = 20;
		int actual = surnames.size();

		assertEquals(expected, actual);

		String expectedSurname = "VanDyke";
		String actualSurname = surnames.get(surnames.size() - 1);

		assertEquals(expectedSurname, actualSurname);
	}

	@Test
	void getValue_ShouldReadValueFromApplicationFile_WhenInputIsValueName() {

		Reader reader = new FileReader();

		var actual = reader.getPropValue("db.driver");
		String expected = "org.h2.Driver";

		assertEquals(expected, actual);
	}
}
