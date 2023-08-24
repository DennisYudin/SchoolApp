package dev.yudin.services.impl;

import static org.junit.jupiter.api.Assertions.*;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.FileReaderTesting;
import dev.yudin.dao.GroupDAO;
import dev.yudin.dao.StudentDAO;
import dev.yudin.dao.impl.GroupsDAOImpl;
import dev.yudin.dao.impl.StudentsDAOImpl;
import dev.yudin.entities.Group;
import dev.yudin.entities.Student;
import dev.yudin.filereader.Reader;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;
import dev.yudin.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class StudentServiceImplTest {

	private StudentService studentService;

	@BeforeEach
	public void setUp() {
		Reader reader = new FileReaderTesting();
		ConnectionManager dataSource = new ConnectionManagerTesting(reader);
		Runnable scriptRunner = new ScriptExecutor(dataSource);

		scriptRunner.run("test-databaseStructure.sql");

		StudentDAO studentDAO = new StudentsDAOImpl(dataSource);
		studentService = new StudentServiceImpl(studentDAO);
	}

	@Test
	void convert_ShouldConvertListIntoMap_WhenInputIsListOfStudents() {
		Student student = new Student();
		student.setId(1);
		student.setFirstName("Dennis");
		student.setLastName("Yudin");

		var actual = studentService.convert(List.of(student));

		assertTrue(actual.containsKey(new Student("Dennis", "Yudin")));
		assertEquals(1, actual.get(new Student("Dennis", "Yudin")));
	}
}