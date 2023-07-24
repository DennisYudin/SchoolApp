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
	private CoursesService coursesService;

	public DBInitializer(Runnable scriptRunner, DataGenerator dataGenerator,
						 DataDistributor dataDistributor, GroupService groupService,
						 CoursesService coursesService) {
		this.scriptRunner = scriptRunner;
		this.dataGenerator = dataGenerator;
		this.dataDistributor = dataDistributor;
		this.groupService = groupService;
		this.coursesService = coursesService;
	}

	public void init() {
		//todo create tables
		scriptRunner.run(DATABASE_STRUCTURE_FILE);

		//todo prepare data
		var students = dataGenerator.generateStudents(200);
		var groups = dataGenerator.generateGroups(10);
		var courses = dataGenerator.getCourses();

		var groupsWithStudents = dataDistributor.assignStudentsIntoGroups(groups, students);
		var studentsWihoutGroups = dataDistributor.getStudentsWithoutGroups(groupsWithStudents, students);
		var studentsWithCourses = dataDistributor.assignStudentsIntoCourses(students, courses);

		//todo populate tables with data using Services
		groupService.save(groups); // get Id
		coursesService.save(courses); // get Id


	}

}
