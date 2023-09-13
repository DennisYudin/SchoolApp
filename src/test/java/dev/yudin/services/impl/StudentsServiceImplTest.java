package dev.yudin.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.Manager;
import dev.yudin.dao.StudentDAO;
import dev.yudin.dao.impl.StudentsDAOImpl;
import dev.yudin.entities.Student;
import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;
import dev.yudin.services.StudentsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class StudentsServiceImplTest {

	private StudentsService studentsService;

	@BeforeEach
	public void setUp() {
		Reader reader = new FileReader();
		Manager dataSource = new ConnectionManagerTesting(reader);
		Runnable scriptRunner = new ScriptExecutor(dataSource);

		scriptRunner.run("test-databaseStructure.sql");

		StudentDAO studentDAO = new StudentsDAOImpl(dataSource);
		studentsService = new StudentsServiceImpl(studentDAO);
	}

	@Test
	void convert_ShouldConvertListIntoMap_WhenInputIsListOfStudents() {
		Student student = new Student();
		student.setId(1);
		student.setFirstName("Dennis");
		student.setLastName("Yudin");

		var actual = studentsService.convert(List.of(student));

		assertTrue(actual.containsKey(new Student("Dennis", "Yudin")));
		assertEquals(1, actual.get(new Student("Dennis", "Yudin")));
	}
}