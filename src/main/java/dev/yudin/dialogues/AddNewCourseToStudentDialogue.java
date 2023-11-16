package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.entities.Course;
import dev.yudin.entities.Student;
import dev.yudin.entities.StudentCourseDTO;
import dev.yudin.exceptions.DialogueException;
import dev.yudin.services.CoursesService;
import dev.yudin.services.StudentsCoursesService;
import dev.yudin.services.StudentsService;

import java.util.List;
import java.util.Scanner;

public class AddNewCourseToStudentDialogue implements Dialogue {
	public static final String STUDENT_MESSAGE = "Enter exists student's name and last name [Example: ";
	public static final String ERROR_MESSAGE = "There is no such student in DB: [";
	public static final String INCORRECT_NAME_OR_LAST_NAME_MESSAGE = "Incorrect name or last name";
	private Console inputHandler;
	private StudentsService studentsService;
	private CoursesService coursesService;
	private StudentsCoursesService studentsCoursesService;
	public AddNewCourseToStudentDialogue(Console inputHandler,
										 StudentsService studentsService,
										 CoursesService coursesService,
										 StudentsCoursesService studentsCoursesService) {
		this.inputHandler = inputHandler;
		this.studentsService = studentsService;
		this.coursesService = coursesService;
		this.studentsCoursesService = studentsCoursesService;
	}

	@Override
	public void start(Scanner scanner) {
		Student exampleStudent = studentsService.getBy(1).orElseThrow(DialogueException::new);
		String studentNameInput = inputHandler.readString(STUDENT_MESSAGE + exampleStudent.getFirstName() + " " + exampleStudent.getLastName() + "]: ", scanner);

		String[] preparedInput = studentNameInput.trim().split("\\s+");

		if (preparedInput.length != 2) {
			throw new DialogueException(INCORRECT_NAME_OR_LAST_NAME_MESSAGE);
		}
		Student enteredStudent = new Student();
		enteredStudent.setFirstName(preparedInput[0]);
		enteredStudent.setLastName(preparedInput[1]);

		System.out.println();
		System.out.println(enteredStudent.getFirstName() + "'s current courses:");
		List<String> visitedCourses = coursesService.findAllBy(enteredStudent);
		if (visitedCourses.isEmpty()) {
			throw new DialogueException("This student does not visit any courses");
		} else {
			visitedCourses.forEach(System.out::println);
		}
		System.out.println();

		List<Student> actualStudentsInTable = studentsService.findAll();
		int studentId = 0;
		if (actualStudentsInTable.contains(enteredStudent)) {
			studentId = studentsService.findAll().stream()
					.filter(enteredStudent::equals)
					.mapToInt(Student::getId)
					.findAny()
					.orElseThrow(DialogueException::new);
		} else {
			System.out.println(ERROR_MESSAGE + enteredStudent.getFirstName() + " " + enteredStudent.getLastName() + "]");
		}
		System.out.println("Available courses for this student: ");
		List<Course> courses = coursesService.findAll();
		courses.removeIf(course -> visitedCourses.contains(course.getName()));
		printAsTableFormat(courses);

		System.out.println();
		String courseInput = inputHandler.readString("Enter course name from the list above: ", scanner);

		int courseId = courses.stream()
				.filter(course -> courseInput.equals(course.getName()))
				.mapToInt(Course::getId)
				.findAny()
				.orElseThrow(() -> new DialogueException("Student already assign for this course"));

		StudentCourseDTO studentCourseDTO = new StudentCourseDTO();
		studentCourseDTO.setStudentId(studentId);
		studentCourseDTO.setCourseId(courseId);

		studentsCoursesService.save(studentCourseDTO);

		System.out.println();
		System.out.println("Updated " + enteredStudent.getFirstName() + "'s courses");
		List<String> updatedCourses = coursesService.findAllBy(enteredStudent);
		updatedCourses.forEach(System.out::println);
	}

	private void printAsTableFormat(List<Course> courses) {
		System.out.format("%-15s%-15s%n", "Course name", "Course description");
		for (Course course : courses) {
			System.out.format("%-15s%-15s%n", course.getName(), course.getDescription());
		}
	}
}
