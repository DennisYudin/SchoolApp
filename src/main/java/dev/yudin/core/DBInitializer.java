package dev.yudin.core;

import dev.yudin.script_runner.Runnable;
import dev.yudin.services.CoursesService;
import dev.yudin.services.GroupService;
import dev.yudin.services.StudentService;

public class DBInitializer {
	public static final String DATABASE_STRUCTURE_FILE = "databaseStructure.sql";
	private Runnable scriptRunner;
	private DataGenerator dataGenerator;
	private DataDistributor dataDistributor;
	private GroupService groupService;
	private CoursesService courseService;
	private StudentService studentService;

	public DBInitializer(Runnable scriptRunner, DataGenerator dataGenerator,
						 DataDistributor dataDistributor, GroupService groupService,
						 CoursesService courseService, StudentService studentService) {
		this.scriptRunner = scriptRunner;
		this.dataGenerator = dataGenerator;
		this.dataDistributor = dataDistributor;
		this.groupService = groupService;
		this.courseService = courseService;
		this.studentService = studentService;
	}

	public void init() {
		//todo create tables
		scriptRunner.run(DATABASE_STRUCTURE_FILE);

		//todo prepare data
		var students = dataGenerator.generateStudents(200);
		var groups = dataGenerator.generateGroups(10);
		var courses = dataGenerator.getCourses();

		var groupsWithStudents = dataDistributor.assignStudentsIntoGroups(groups, students);
		var studentsWithoutGroups = dataDistributor.getStudentsWithoutGroups(groupsWithStudents, students);

		var studentsWithCourses = dataDistributor.assignStudentsIntoCourses(students, courses);

		//todo populate tables with data using Services
		groupService.save(groups);
		courseService.save(courses);

		var listOfGroups = groupService.findAll();
		var mapGroups = groupService.convert(listOfGroups);

		var listOfStudents = dataDistributor.merge(groupsWithStudents, studentsWithoutGroups, mapGroups);
		//populate students table
		//using groupsWithStudents and studentsWithoutGroups from line 36, 37
		//to fill fields name | surname | group_id
		studentService.save(listOfStudents);

		//populate student_courses table
		//using studentsWithCourses many - to - many table from line 38
	}
}
