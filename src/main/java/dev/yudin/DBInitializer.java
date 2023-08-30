package dev.yudin;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.Manager;
import dev.yudin.core.DataDistributor;
import dev.yudin.core.DataGenerator;
import dev.yudin.dao.CourseDAO;
import dev.yudin.dao.GroupDAO;
import dev.yudin.dao.StudentDAO;
import dev.yudin.dao.StudentsCoursesDAO;
import dev.yudin.dao.impl.CoursesDAOImpl;
import dev.yudin.dao.impl.GroupsDAOImpl;
import dev.yudin.dao.impl.StudentsCoursesDAOImpl;
import dev.yudin.dao.impl.StudentsDAOImpl;
import dev.yudin.entities.Course;
import dev.yudin.entities.Student;
import dev.yudin.filereader.FileReader;
import dev.yudin.script_runner.Runnable;
import dev.yudin.script_runner.ScriptExecutor;
import dev.yudin.services.CoursesService;
import dev.yudin.services.GroupService;
import dev.yudin.services.StudentCourseService;
import dev.yudin.services.StudentService;
import dev.yudin.services.impl.CourseServiceImpl;
import dev.yudin.services.impl.GroupServiceImpl;
import dev.yudin.services.impl.StudentCourseServiceImpl;
import dev.yudin.services.impl.StudentServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class DBInitializer {
	private static final String DATABASE_STRUCTURE_FILE = "databaseStructure.sql";
	private static final int AMOUNT_STUDENTS = 200;
	private static final int AMOUNT_GROUPS = 10;
	private Manager dataSource = new ConnectionManager();
	private Runnable scriptRunner = new ScriptExecutor(dataSource);
	private DataGenerator dataGenerator = new DataGenerator(new Random(), new FileReader());
	private DataDistributor dataDistributor = new DataDistributor(new Random());
	private GroupDAO groupDAO = new GroupsDAOImpl(dataSource);
	private GroupService groupService = new GroupServiceImpl(groupDAO);
	private CourseDAO courseDAO = new CoursesDAOImpl(dataSource);
	private CoursesService courseService = new CourseServiceImpl(courseDAO);
	private StudentDAO studentDAO = new StudentsDAOImpl(dataSource);
	private StudentService studentService = new StudentServiceImpl(studentDAO);
	private StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAOImpl(dataSource);
	private StudentCourseService studentCourseService = new StudentCourseServiceImpl(studentsCoursesDAO);

	public void init() {
		createAllTables();

		Set<Student> initStudentsData = dataGenerator.generateStudents(AMOUNT_STUDENTS);
		List<String> initGroupsData = dataGenerator.generateGroups(AMOUNT_GROUPS);
		List<Course> initCoursesData = dataGenerator.getCourses();

		fillGroupTable(initGroupsData);
		fillCourseTable(initCoursesData);
		fillStudentTable(initStudentsData, initGroupsData);
		fillStudentCourseTable(initCoursesData);
	}

	private void fillStudentCourseTable(List<Course> initCourses) {
		var coursesFromTable = courseService.findAll();
		var coursesNameID = courseService.convert(coursesFromTable);
		var studentsFromTable = studentService.findAll();
		var studentID = studentService.convert(studentsFromTable);
		Set<Student> studentSet = new HashSet<>(studentsFromTable);
		var studentsWithCourses = dataDistributor.assignStudentsIntoCourses(studentSet, initCourses);
		var studentsCoursesData = dataDistributor.merge(studentsWithCourses, studentID, coursesNameID);

		studentCourseService.save(studentsCoursesData);
	}

	private void fillStudentTable(Set<Student> initStudents, List<String> initGroups) {
		var groupsWithStudents = dataDistributor.assignStudentsIntoGroups(initGroups, initStudents);
		var studentsWithoutGroups = dataDistributor.getStudentsWithoutGroups(groupsWithStudents, initStudents);
		var groupsFromTable = groupService.findAll();
		Map<String, Integer> groupNameID = groupService.convert(groupsFromTable);
		var listOfStudents = dataDistributor.merge(groupsWithStudents, studentsWithoutGroups, groupNameID);

		studentService.save(listOfStudents);
	}

	private void fillCourseTable(List<Course> initCourses) {
		courseService.save(initCourses);
	}

	private void fillGroupTable(List<String> initGroups) {
		groupService.save(initGroups);
	}

	private void createAllTables() {
		scriptRunner.run(DATABASE_STRUCTURE_FILE);
	}
}
