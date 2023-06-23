package dev.yudin;

import org.junit.jupiter.api.Test;

class DBInitializerTest {

	@Test
	void name() {

		DBInitializer initializer = new DBInitializer();

		var actual = initializer.mapToObject("src/test/resources/courses.txt");

		System.out.println(actual);
	}
}