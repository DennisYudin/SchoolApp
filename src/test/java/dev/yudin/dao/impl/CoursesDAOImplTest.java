package dev.yudin.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.Manager;
import dev.yudin.dao.CourseDAO;
import dev.yudin.entities.Course;
import dev.yudin.filereader.FileReader;
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
	private CourseDAO coursesDAO;

	@BeforeEach
	public void setUp() {
		Reader reader = new FileReader();
		Manager manager = new ConnectionManagerTesting(reader);
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
	void save_ShouldSaveDataIntoTable_WhenInputIsListOfObjects() {
		List<Course> coursesTableBefore = coursesDAO.findAll();

		assumeTrue(coursesTableBefore.isEmpty());

		Course course = new Course();
		course.setName("Algebra");
		course.setDescription("Something about numbers...");
		List<Course> courses = List.of(course);

		coursesDAO.save(courses);

		List<Course> coursesTableAfter = coursesDAO.findAll();

		assertFalse(coursesTableAfter.isEmpty());
	}

	@Test
	@Order(2)
	void findAll_ShouldReturnListOfCourses_WhenCallMethod() {
		Course expectedCourse = new Course();
		expectedCourse.setId(1);
		expectedCourse.setName("Algebra");
		expectedCourse.setDescription("Something about numbers...");
		List<Course> courses = List.of(expectedCourse);

		coursesDAO.save(courses);

		List<Course> actual = coursesDAO.findAll();
		var actualCourse = actual.get(0);

		assertEquals(expectedCourse, actualCourse);
	}
}
