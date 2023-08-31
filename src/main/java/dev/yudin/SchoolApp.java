package dev.yudin;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.Manager;
import dev.yudin.dao.StudentDAO;
import dev.yudin.dao.StudentsCoursesDAO;
import dev.yudin.dao.impl.StudentsCoursesDAOImpl;
import dev.yudin.dao.impl.StudentsDAOImpl;
import dev.yudin.entities.Student;
import dev.yudin.services.StudentsCoursesService;
import dev.yudin.services.StudentsService;
import dev.yudin.services.impl.StudentsCoursesServiceImpl;
import dev.yudin.services.impl.StudentsServiceImpl;

import java.util.List;

public class SchoolApp {
	public static void main(String[] args) {

		Manager dataSource = new ConnectionManager();
		StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAOImpl(dataSource);
		StudentsCoursesService studentsCoursesService = new StudentsCoursesServiceImpl(studentsCoursesDAO);

		StudentDAO studentDAO = new StudentsDAOImpl(dataSource);
		StudentsService studentsService = new StudentsServiceImpl(studentDAO);

		DBInitializer dbInitializer = new DBInitializer();
		try {
			dbInitializer.init();

			studentsCoursesService.findAll().forEach(System.out::println);

			studentsService.save(List.of(new Student("Dennis", "Yduin")));
		} catch (Exception ex) {
//			System.err.println(ex.getMessage());
		}
	}
}
