package dev.yudin.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.Manager;
import dev.yudin.dao.CourseDAO;
import dev.yudin.dao.impl.CoursesDAOImpl;
import dev.yudin.entities.Course;
import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;
import dev.yudin.services.CoursesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class CourseServiceImplTest {
	private CoursesService coursesService;

	@BeforeEach
	public void setUp() {
		Reader reader = new FileReader();
		Manager dataSource = new ConnectionManagerTesting(reader);
		Runnable scriptRunner = new ScriptExecutor(dataSource);

		scriptRunner.run("test-databaseStructure.sql");

		CourseDAO coursesDAO = new CoursesDAOImpl(dataSource);
		coursesService = new CourseServiceImpl(coursesDAO);
	}

	@Test
	void convert_ShouldConvertListIntoMap_WhenInputIsListOfCourses() {
		Course course = new Course();
		course.setId(1);
		course.setName("Algebra");
		course.setDescription("Difficult subject");

		var actual = coursesService.convert(List.of(course));

		assertTrue(actual.containsKey("Algebra"));
		assertEquals(1, actual.get("Algebra"));
	}
}