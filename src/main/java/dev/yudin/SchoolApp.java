package dev.yudin;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.Manager;
import dev.yudin.dao.StudentDAO;
import dev.yudin.dao.StudentsCoursesDAO;
import dev.yudin.dao.impl.StudentsCoursesDAOImpl;
import dev.yudin.dao.impl.StudentsDAOImpl;
import dev.yudin.entities.Student;
import dev.yudin.services.StudentCourseService;
import dev.yudin.services.StudentService;
import dev.yudin.services.impl.StudentCourseServiceImpl;
import dev.yudin.services.impl.StudentServiceImpl;

import java.util.List;

public class SchoolApp {
	public static void main(String[] args) {

		Manager dataSource = new ConnectionManager();
		StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAOImpl(dataSource);
		StudentCourseService studentCourseService = new StudentCourseServiceImpl(studentsCoursesDAO);

		StudentDAO studentDAO = new StudentsDAOImpl(dataSource);
		StudentService studentService = new StudentServiceImpl(studentDAO);

		DBInitializer dbInitializer = new DBInitializer();
		try {
			dbInitializer.init();

			studentCourseService.findAll().forEach(System.out::println);

			studentService.save(List.of(new Student("Dennis", "Yduin")));
		} catch (Exception ex) {
//			System.err.println(ex.getMessage());
		}
	}
}
