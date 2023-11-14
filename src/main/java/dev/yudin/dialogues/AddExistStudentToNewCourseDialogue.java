package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.entities.Course;
import dev.yudin.entities.GroupsAmountStudentDTO;
import dev.yudin.entities.Student;
import dev.yudin.exceptions.DialogueException;
import dev.yudin.services.CoursesService;
import dev.yudin.services.StudentsService;

import java.util.List;
import java.util.Scanner;

public class AddExistStudentToNewCourseDialogue implements Dialogue {
	public static final String TABLE_TITLE = "Updated Student's table:";
	public static final String STUDENT_MESSAGE = "Enter student's name and last name [Example: ";
	public static final String ERROR_MESSAGE = "There is no such student in DB: [";
	public static final String INCORRECT_NAME_OR_LAST_NAME_MESSAGE = "Incorrect name or last name";
	private Console inputHandler;
	private StudentsService studentsService;
	private CoursesService coursesService;
	public AddExistStudentToNewCourseDialogue(Console inputHandler,
											  StudentsService studentsService, CoursesService coursesService) {
		this.inputHandler = inputHandler;
		this.studentsService = studentsService;
		this.coursesService = coursesService;
	}

	@Override
	public void start(Scanner scanner) {
		Student existStudent = studentsService.getBy(1)
				.orElseThrow(DialogueException::new);
		String input = inputHandler.readString(STUDENT_MESSAGE + existStudent.getFirstName() + " " + existStudent.getLastName() + "]: ", scanner);

		String[] preparedInput = input.trim().split("\\s+");

		if (preparedInput.length != 2) {
			throw new DialogueException(INCORRECT_NAME_OR_LAST_NAME_MESSAGE);
		}
		Student enteredStudent = new Student();
		enteredStudent.setFirstName(preparedInput[0]);
		enteredStudent.setLastName(preparedInput[1]);

		//todo show list of courses for this student
		System.out.println(enteredStudent.getFirstName() + "'s current courses");
		var visitedCourses = coursesService.findAllBy(enteredStudent);
		visitedCourses.forEach(System.out::println);

		System.out.println();

		List<Student> actualStudentsInTable = studentsService.findAll();
		if (!actualStudentsInTable.contains(enteredStudent)) {
			System.out.println(ERROR_MESSAGE + enteredStudent.getFirstName() + " " + enteredStudent.getLastName() + "]");
		} else {
			int studentId = studentsService.findAll().stream()
					.filter(student -> enteredStudent.equals(student))
					.mapToInt(Student::getId)
					.findAny()
					.orElseThrow();
		}
		System.out.println("Please pick the course");
		List<Course> courses = coursesService.findAll();
		printAsTableFormat(courses);

//		String input = inputHandler.readString(STUDENT_MESSAGE + existStudent.getFirstName() + " " + existStudent.getLastName() + "]: ", scanner);
	}

	private void printAsTableFormat(List<Course> courses) {
		System.out.format("%-15s%-15s%n", "Course name", "Course description");
		for (var course : courses) {
			System.out.format("%-15s%-15s%n", course.getName(), course.getDescription());
		}
	}
}
