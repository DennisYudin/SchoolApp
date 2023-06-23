package dev.yudin.dao.impl;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.FileReaderTesting;
import dev.yudin.dao.CoursesDAO;
import dev.yudin.entities.Course;
import dev.yudin.filereader.Reader;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CoursesDAOImplTest {
	private CoursesDAO coursesDAO;

	@BeforeEach
	public void setUp() {
		Reader reader = new FileReaderTesting();
		ConnectionManager manager = new ConnectionManagerTesting(reader);

		Runnable scriptRunner = new ScriptExecutor(manager);

		scriptRunner.run("test-databaseStructure.sql");

		coursesDAO = new CoursesDAOImpl(manager);
	}

	@Test
	@Order(0)
	void findAll_ShouldReturnEmptyList_WhenCallMethod() {

		List<Course> actual = coursesDAO.findAll();

		assertTrue(actual.isEmpty());
	}

	@Test
	@Order(1)
	void fillCourseTable_ShouldPopulateTable_WhenInputIsListOfObjects() {
		List<Course> before = coursesDAO.findAll();

		assumeTrue(before.isEmpty());

		Course course = new Course();
		course.setName("Algebra");
		course.setDescription("Something about numbers...");
		List<Course> courses = List.of(course);

		coursesDAO.fillCourseTable(courses);

		List<Course> after = coursesDAO.findAll();

		assertFalse(after.isEmpty());
	}
}
