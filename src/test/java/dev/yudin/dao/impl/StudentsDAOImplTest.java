package dev.yudin.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.FileReaderTesting;
import dev.yudin.dao.StudentDAO;
import dev.yudin.entities.Student;
import dev.yudin.entities.StudentDTO;
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
class StudentsDAOImplTest {

	private StudentDAO studentDAO;

	@BeforeEach
	public void setUp() {
		Reader reader = new FileReaderTesting();
		ConnectionManager manager = new ConnectionManagerTesting(reader);

		Runnable scriptRunner = new ScriptExecutor(manager);

		scriptRunner.run("test-databaseStructure.sql");
		scriptRunner.run("fillGroupTable.sql");
		scriptRunner.run("fillStudentTable.sql");
		scriptRunner.run("fillCourseTable.sql");
		scriptRunner.run("fillStudentsCourseTable.sql");

		studentDAO = new StudentsDAOImpl(manager);
	}

	@Test
	@Order(0)
	void findAll_ShouldListOfStudents_WhenCallMethod() {

		List<Student> actual = studentDAO.findAll();

		assertFalse(actual.isEmpty());
	}

	@Test
	@Order(1)
	void save_ShouldSaveListOfStudentIntoTable_WhenInputListOfStudents() {
		Student newStudent = new Student();
		newStudent.setFirstName("Ivanov");
		newStudent.setLastName("Ivan");
		newStudent.setGroupId(1);

		studentDAO.save(List.of(newStudent));

		List<Student> studentsInTable = studentDAO.findAll();

		assertTrue(studentsInTable.contains(newStudent));
	}

	@Test
	@Order(2)
	void save_ShouldSaveStudentWithoutGroupId_WhenInputListOfStudents() {
		Student expectedStudent = new Student();
		expectedStudent.setFirstName("Ivanov");
		expectedStudent.setLastName("Ivan");

		studentDAO.save(List.of(expectedStudent));

		List<Student> studentsInTable = studentDAO.findAll();

		assertTrue(studentsInTable.contains(expectedStudent));
	}

	@Test
	void findAllBy_ShouldFindAllStudentsAssignToTheCourse_WhenInputIsCourseName() {

		var actual = studentDAO.findAllBy("Algebra");
		var actualStudent = actual.get(0);

		StudentDTO expectedStudent = new StudentDTO();
		expectedStudent.setId(1);
		expectedStudent.setFirstName("Dennis");
		expectedStudent.setLastName("Yudin");

		assertEquals(expectedStudent, actualStudent);
	}

	@Test
	void findAllBy_ShouldReturnEmptyList_WhenInputIsCourseName() {
		var actual = studentDAO.findAllBy("Biology");

		assertTrue(actual.isEmpty());
	}
}