package dev.yudin.dao.impl;

import dev.yudin.DBInitializer;
import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.ConnectionManagerTesting;
import dev.yudin.connection.FileReaderTesting;
import dev.yudin.dao.CoursesDAO;
import dev.yudin.entities.Course;
import dev.yudin.filereader.Reader;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;
import org.junit.jupiter.api.Test;

import java.util.List;

class CoursesDAOImplTest {

	@Test
	void name() {
		//todo init test DB

		DBInitializerTesting.init();

		Reader reader = new FileReaderTesting();
		ConnectionManager manager = new ConnectionManagerTesting(reader);

		Runnable scriptRunner = new ScriptExecutor(manager);

		scriptRunner.run("test-databaseStructure.sql");

		CoursesDAO coursesDAO = new CoursesDAOImpl(manager);

		Course course = new Course();
		course.setName("Algebra");
		course.setDescription("bla bla");
		List<Course> courseList = List.of(course);

		coursesDAO.fillCourseTable(courseList);

		var actual = coursesDAO.findAll();

		System.out.println(actual);
	}
}