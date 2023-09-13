package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.entities.Student;
import dev.yudin.exceptions.DialogueException;
import dev.yudin.services.StudentsService;

import java.util.Scanner;

public class DeleteByIdStudentDialogue implements Dialogue {
	private Console inputHandler;
	private StudentsService studentsService;

	public DeleteByIdStudentDialogue(Console inputHandler, StudentsService studentsService) {
		this.inputHandler = inputHandler;
		this.studentsService = studentsService;
	}

	@Override
	public void start(Scanner scanner) {
		var actualStudentsTable = studentsService.findAll();
		int maxLimit = actualStudentsTable.size();

		int id = inputHandler.readInt("Enter student's id from range[1-" + maxLimit + "]: ", scanner);
		scanner.nextLine();

		var deletedStudent = studentsService.getBy(id);

		if (deletedStudent.isPresent()) {
			studentsService.deleteById(id);

			printAsTableFormat(deletedStudent.get());
		} else {
			throw new DialogueException("Did not find student with id = " + id);
		}
	}

	private void printAsTableFormat(Student student) {
		System.out.println("From students table was deleted:");
		System.out.format("%-15s%-15s%-15s%n", "ID", "First name", "Last name");
		System.out.format("%-15s%-15s%-15s%n", student.getId(), student.getFirstName(), student.getLastName());
	}
}
