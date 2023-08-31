package dev.yudin;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.Manager;
import dev.yudin.dao.StudentDAO;
import dev.yudin.dao.StudentsCoursesDAO;
import dev.yudin.dao.impl.StudentsCoursesDAOImpl;
import dev.yudin.dao.impl.StudentsDAOImpl;
import dev.yudin.dialogues.Dialogue;
import dev.yudin.dialogues.InitDialogue;
import dev.yudin.entities.Student;
import dev.yudin.services.StudentsCoursesService;
import dev.yudin.services.StudentsService;
import dev.yudin.services.impl.StudentsCoursesServiceImpl;
import dev.yudin.services.impl.StudentsServiceImpl;

import java.util.List;
import java.util.Scanner;

public class SchoolApp {
	public static void main(String[] args) {

		Manager dataSource = new ConnectionManager();
		StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAOImpl(dataSource);
		StudentsCoursesService studentsCoursesService = new StudentsCoursesServiceImpl(studentsCoursesDAO);

		StudentDAO studentDAO = new StudentsDAOImpl(dataSource);
		StudentsService studentsService = new StudentsServiceImpl(studentDAO);

		Dialogue initDialogue = new InitDialogue();
		DBInitializer dbInitializer = new DBInitializer();
		try (Scanner scanner = new Scanner(System.in)){
			dbInitializer.init();

			initDialogue.start(scanner);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
}
