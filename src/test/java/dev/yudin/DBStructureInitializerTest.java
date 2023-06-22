package dev.yudin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DBStructureInitializerTest {

	@Test
	void name() {

		DBStructureInitializer initializer = new DBStructureInitializer();

		var actual = initializer.mapToObject("src/test/resources/courses.txt");

		System.out.println(actual);
	}
}