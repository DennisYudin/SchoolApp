package dev.yudin.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.FileReaderTesting;
import dev.yudin.dao.StudentsCoursesDAO;
import dev.yudin.entities.StudentCourseDTO;
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
class StudentsCoursesDAOImplTest {
	private StudentsCoursesDAO studentsCoursesDAO;

	@BeforeEach
	public void setUp() {
		Reader reader = new FileReaderTesting();
		ConnectionManager dataSource = new ConnectionManagerTesting(reader);
		Runnable scriptRunner = new ScriptExecutor(dataSource);

		scriptRunner.run("test-databaseStructure.sql");
		scriptRunner.run("fillGroupTable.sql");
		scriptRunner.run("fillStudentTable.sql");
		scriptRunner.run("fillCourseTable.sql");

		studentsCoursesDAO = new StudentsCoursesDAOImpl(dataSource);
	}

	@Test
	@Order(0)
	void findAll_ShouldReturnEmptyList_WhenCallMethod() {

		List<StudentCourseDTO> actual = studentsCoursesDAO.findAll();

		assertTrue(actual.isEmpty());
	}

	@Test
	@Order(1)
	void save_ShouldSaveDataIntoStudentsCoursesTable_WhenInputIsListOfObjects() {
		List<StudentCourseDTO> studentsCoursesTableBefore = studentsCoursesDAO.findAll();

		assumeTrue(studentsCoursesTableBefore.isEmpty());

		StudentCourseDTO expectedDTO = new StudentCourseDTO();
		expectedDTO.setStudentId(1);
		expectedDTO.setCourseId(1);

		List<StudentCourseDTO> dtos = List.of(expectedDTO);

		studentsCoursesDAO.save(dtos);

		List<StudentCourseDTO> studentsCoursesTableAfter = studentsCoursesDAO.findAll();
		var actualDTO = studentsCoursesTableAfter.get(0);

		assertEquals(expectedDTO, actualDTO);
	}
}