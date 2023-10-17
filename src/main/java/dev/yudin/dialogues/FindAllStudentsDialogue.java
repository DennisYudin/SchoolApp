package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.entities.Course;
import dev.yudin.entities.StudentDTO;
import dev.yudin.services.CoursesService;
import dev.yudin.services.StudentsService;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class FindAllStudentsDialogue implements Dialogue {
	public static final String TABLE_TITLE = "List of students who go to ";
	public static final String COURSE_MESSAGE = "Enter course name: ";
	private Console inputHandler;
	private StudentsService studentsService;
	private CoursesService coursesService;

	public FindAllStudentsDialogue(Console inputHandler,
								   StudentsService studentsService,
								   CoursesService coursesService) {
		this.inputHandler = inputHandler;
		this.studentsService = studentsService;
		this.coursesService = coursesService;
	}

	@Override
	public void start(Scanner scanner) {
		System.out.println();
		System.out.println("Pick the course name");
		var courses = coursesService.findAll();
		var coursesName = courses.stream().map(Course::getName).collect(Collectors.joining(", "));
		System.out.println(coursesName);

		String input = inputHandler.readString(COURSE_MESSAGE, scanner);

		System.out.println();
		System.out.println(TABLE_TITLE + input);

		var result = studentsService.findAllBy(input);
		if (result.isEmpty()) {
			System.out.println("there is no data with such course name: [" + input + "]");
		} else {
			printAsTableFormat(result);
		}
	}

	private void printAsTableFormat(List<StudentDTO> result) {
		System.out.format("%-15s%-15s%-15s%n", "ID", "First name", "Last name");
		for (var dto : result) {
			System.out.format("%-15s%-15s%-15s%n", dto.getId(), dto.getFirstName(), dto.getLastName());
		}
	}
}
