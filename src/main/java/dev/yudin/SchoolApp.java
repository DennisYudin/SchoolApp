package dev.yudin;

import dev.yudin.dao.impl.CoursesDAOImpl;
import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;

/**
 * Hello world!
 */
public class SchoolApp {
	public static void main(String[] args) {

		//todo init database structer
		DBStructureInitializer dbStructure = new DBStructureInitializer();

		dbStructure.init();

		var courses = dbStructure.mapToObject("src/main/resources/courses.txt");

		dbStructure.fillInCourseTable(courses);

		CoursesDAOImpl dao = new CoursesDAOImpl();

		var all = dao.findAll();

		System.out.println(all);
	}
}
