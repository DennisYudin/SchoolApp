package dev.yudin.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.Manager;
import dev.yudin.dao.CourseDAO;
import dev.yudin.entities.Course;
import dev.yudin.entities.Student;
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
		scriptRunner.run("fillGroupTable.sql");
		scriptRunner.run("fillStudentTable.sql");
		scriptRunner.run("fillCourseTable.sql");
		scriptRunner.run("fillStudentsCourseTable.sql");

		coursesDAO = new CoursesDAOImpl(manager);
	}

	@Test
	@Order(0)
	void findAll_ShouldReturnListCourses_WhenCallMethod() {

		List<Course> actual = coursesDAO.findAll();
		System.out.println(actual);
		assertFalse(actual.isEmpty());
	}

	@Test
	@Order(1)
	void save_ShouldSaveNewCourseIntoTable_WhenInputIsListOfObjects() {
		List<Course> coursesTableBefore = coursesDAO.findAll();

		int actualSizeBeforeChange = coursesTableBefore.size();
		int expectedTableSize = 2;
		assumeTrue(actualSizeBeforeChange == expectedTableSize);

		Course expectedCourse = new Course();
		expectedCourse.setName("Biology");
		expectedCourse.setDescription("Something about numbers...");
		List<Course> courses = List.of(expectedCourse);

		coursesDAO.save(courses);

		List<Course> coursesTableAfter = coursesDAO.findAll();
		int actualSizeAfterChange = coursesTableAfter.size();
		int expectedSize = 3;

		assertEquals(expectedSize, actualSizeAfterChange);
	}

	@Test
	@Order(3)
	void findAll_ShouldReturnListOfCourses_WhenInputIsStudent() {
		Student student = new Student();
		student.setFirstName("Dennis");
		student.setLastName("Yudin");

		List<String> courses = coursesDAO.findAllBy(student);

		int expectedSize = 1;
		int actualSize = courses.size();

		assertEquals(expectedSize, actualSize);

		String expectedCourse = "Algebra";
		String actualCourse = courses.get(0);
		assertEquals(expectedCourse, actualCourse);
	}
	@Test
	@Order(4)
	void findAll_ShouldReturnEmptyList_WhenInputIsStudentHasNoCourses() {
		Student student = new Student();
		student.setFirstName("FakeName");
		student.setLastName("FakeSurname");

		List<String> courses = coursesDAO.findAllBy(student);

		assertTrue(courses.isEmpty());
	}
}
