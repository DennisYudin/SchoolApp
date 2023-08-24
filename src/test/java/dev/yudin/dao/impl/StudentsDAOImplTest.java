package dev.yudin.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.FileReaderTesting;
import dev.yudin.dao.StudentDAO;
import dev.yudin.entities.Student;
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

		studentDAO = new StudentsDAOImpl(manager);
	}

	@Test
	@Order(0)
	void findAll_ShouldReturnEmptyList_WhenCallMethod() {

		List<Student> actual = studentDAO.findAll();

		assertTrue(actual.isEmpty());
	}

	@Test
	@Order(1)
	void save_ShouldSaveListOfStudentIntoTable_WhenInputListOfStudents() {
		List<Student> studentsTableStateBefore = studentDAO.findAll();

		assumeTrue(studentsTableStateBefore.isEmpty());

		Student expectedStudent = new Student();
		expectedStudent.setId(1);
		expectedStudent.setFirstName("dennis");
		expectedStudent.setLastName("yudin");
		expectedStudent.setGroupId(1);

		studentDAO.save(List.of(expectedStudent));

		List<Student> studentsTableStateAfter = studentDAO.findAll();
		var actualStudent = studentsTableStateAfter.get(0);

		assertEquals(expectedStudent, actualStudent);
	}

	@Test
	@Order(2)
	void save_ShouldSaveStudentWithoutGroupId_WhenInputListOfStudents() {
		List<Student> studentsTableStateBefore = studentDAO.findAll();

		assumeTrue(studentsTableStateBefore.isEmpty());

		Student expectedStudent = new Student();
		expectedStudent.setId(1);
		expectedStudent.setFirstName("dennis");
		expectedStudent.setLastName("yudin");

		studentDAO.save(List.of(expectedStudent));

		List<Student> studentsTableStateAfter = studentDAO.findAll();
		var actualStudent = studentsTableStateAfter.get(0);

		assertEquals(expectedStudent, actualStudent);
	}
}