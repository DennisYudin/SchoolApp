package dev.yudin.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.Manager;
import dev.yudin.dao.StudentDAO;
import dev.yudin.entities.Student;
import dev.yudin.entities.StudentDTO;
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
class StudentsDAOImplTest {

	private StudentDAO studentDAO;

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
		System.out.println(studentsInTable);
		assertTrue(studentsInTable.contains(expectedStudent));
	}

	@Test
	@Order(3)
	void save_ShouldNewSaveStudent_WhenInputIsSingleStudent() {
		Student expectedStudent = new Student();
		expectedStudent.setFirstName("Ivanov");
		expectedStudent.setLastName("Ivan");

		studentDAO.save(expectedStudent);

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

	@Test
	void deleteById_ShouldDeleteStudentById_WhenInputIsID() {
		studentDAO.deleteById(1);

		var actualStudentsTable = studentDAO.findAll();

		assertTrue(actualStudentsTable.isEmpty());
	}
	@Test
	void deleteById_ShouldDoNothing_WhenInputIsDoesNotExistStudentId() {
		studentDAO.deleteById(100);

		var actualStudentsTable = studentDAO.findAll();

		assertFalse(actualStudentsTable.isEmpty());
	}

	@Test
	void getBy_ShouldReturnStudent_WhenInputIsId() {

		int id = 1;
		var actual = studentDAO.getBy(id);

		var expected = new Student();
		expected.setId(1);
		expected.setFirstName("Dennis");
		expected.setLastName("Yudin");
		expected.setGroupId(1);

		assertEquals(expected, actual.get());
	}

	@Test
	void getBy_ShouldReturnEmptyOptional_WhenInputIsDoesNotExistId() {
		int doesNotExistID = 10;
		var actual = studentDAO.getBy(doesNotExistID);

		assertTrue(actual.isEmpty());
	}
}