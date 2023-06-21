package dev.yudin.filereader;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class FileReaderTest {

	@Test
	void name() {

		Reader reader = new FileReader();

		var actual = reader.readFile("src/main/resources/students");

		System.out.println(actual);

	}
}