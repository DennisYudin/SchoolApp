package dev.yudin.filereader;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FileReaderTest {
	public static final String PROPERTIES_TEST_FILE = "test-application.properties";
	private final Reader reader = new FileReader();

	@Test
	void read_ShouldReadFile_WhenInputIsPathToTheFile() {

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

		var actual = reader.getPropValue("test.db.driver", PROPERTIES_TEST_FILE);
		String expected = "org.h2.Driver";

		assertEquals(expected, actual);
	}
}
