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
import dev.yudin.filereader.Reader;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.List;

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

		Student student = new Student();
		student.setFirstName("dennis");
		student.setLastName("yudin");
		student.setGroupId(1);

		studentDAO.save(List.of(student));

		List<Student> studentsTableStateAfter = studentDAO.findAll();
		assertFalse(studentsTableStateAfter.isEmpty());
	}

	@Test
	@Order(2)
	void save_ShouldSaveListOfStudentIntoTable_WhenInputListOfStudents2() {
		List<Student> studentsTableStateBefore = studentDAO.findAll();

		assumeTrue(studentsTableStateBefore.isEmpty());

		Student student = new Student();
		student.setFirstName("dennis");
		student.setLastName("yudin");
//		student.setGroupId(0); //todo should return null??

		studentDAO.save(List.of(student));

		List<Student> studentsTableStateAfter = studentDAO.findAll();
		assertFalse(studentsTableStateAfter.isEmpty());

		System.out.println(studentsTableStateAfter);
	}
}