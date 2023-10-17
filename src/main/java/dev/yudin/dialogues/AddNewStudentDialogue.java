package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.entities.Student;
import dev.yudin.exceptions.DialogueException;
import dev.yudin.services.StudentsService;

import java.util.Scanner;

public class AddNewStudentDialogue implements Dialogue {
	public static final String TABLE_TITLE = "Updated Student's table:";
	public static final String STUDENT_MESSAGE = "Enter student's name and last name [Example: Dennis Yudin]: ";
	public static final String ERROR_MESSAGE = "Already exist in table: [";
	public static final String INCORRECT_NAME_OR_LAST_NAME_MESSAGE = "Incorrect name or last name";
	private Console inputHandler;
	private StudentsService studentsService;

	public AddNewStudentDialogue(Console inputHandler, StudentsService studentsService) {
		this.inputHandler = inputHandler;
		this.studentsService = studentsService;
	}

	@Override
	public void start(Scanner scanner) {
		var input = inputHandler.readString(STUDENT_MESSAGE, scanner);

		String[] preparedInput = input.trim().split("\\s+");

		if (preparedInput.length <= 1) {
			throw new DialogueException(INCORRECT_NAME_OR_LAST_NAME_MESSAGE);
		}
		Student newStudent = new Student();
		newStudent.setFirstName(preparedInput[0]);
		newStudent.setLastName(preparedInput[1]);

		var actualStudentsInTable = studentsService.findAll();
		if (actualStudentsInTable.contains(newStudent)) {
			System.out.println(ERROR_MESSAGE + newStudent.getFirstName() + " " + newStudent.getLastName() + "]");
		} else {
			studentsService.save(newStudent);

			System.out.println();
			System.out.println(TABLE_TITLE);
			var updatedStudentsTable = studentsService.findAll();
			var lastAddedStudent = updatedStudentsTable.get(actualStudentsInTable.size());

			printAsTableFormat(lastAddedStudent);
		}
	}

	private void printAsTableFormat(Student student) {
		System.out.format("%-15s%-15s%-15s%n", "ID", "First name", "Last name");
		System.out.format("%-15s%-15s%-15s%n", student.getId(), student.getFirstName(), student.getLastName());
	}
}
