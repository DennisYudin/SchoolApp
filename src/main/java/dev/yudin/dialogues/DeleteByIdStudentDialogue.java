package dev.yudin.dialogues;

import dev.yudin.console.Console;
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

		if (id > maxLimit) {
			throw new DialogueException("ID exceeded limit : was " + id + " " + " last ID in table: " + maxLimit);
		}
		//todo get by ID

		studentsService.deleteById(id);
	}
}
