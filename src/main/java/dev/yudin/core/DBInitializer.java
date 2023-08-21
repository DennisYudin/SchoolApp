package dev.yudin.core;

import dev.yudin.script_runner.Runnable;
import dev.yudin.services.CoursesService;
import dev.yudin.services.GroupService;

public class DBInitializer {
	public static final String DATABASE_STRUCTURE_FILE = "databaseStructure.sql";
//	Manager dataSource = new ConnectionManager();
//	Runnable scriptRunner = new ScriptExecutor(dataSource);
	private Runnable scriptRunner;
	private DataGenerator dataGenerator;
	private DataDistributor dataDistributor;
	private GroupService groupService;
	private CoursesService courseService;

	public DBInitializer(Runnable scriptRunner, DataGenerator dataGenerator,
						 DataDistributor dataDistributor, GroupService groupService,
						 CoursesService courseService) {
		this.scriptRunner = scriptRunner;
		this.dataGenerator = dataGenerator;
		this.dataDistributor = dataDistributor;
		this.groupService = groupService;
		this.courseService = courseService;
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
		groupService.save(groups); // get Id
		courseService.save(courses); // get Id
		//todo stop here 20/08/23
		//populate students table
		//using groupsWithStudents and studentsWithoutGroups from line 36, 37
		//to fill fields name | surname | group_id

		//populate student_courses table
		//using studentsWithCourses many - to - many table from line 38
	}

}
