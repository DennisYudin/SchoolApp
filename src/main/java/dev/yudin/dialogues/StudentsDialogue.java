package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.entities.StudentDTO;
import dev.yudin.services.StudentsService;

import java.util.List;
import java.util.Scanner;

public class StudentsDialogue implements Dialogue {
	public static final String TABLE_TITLE = "Students table:";

	private Console inputHandler;
	private StudentsService studentsService;

	public StudentsDialogue(Console inputHandler, StudentsService studentsService) {
		this.inputHandler = inputHandler;
		this.studentsService = studentsService;
	}

	@Override
	public void start(Scanner scanner) {
		var input = inputHandler.readString("Enter course name: ", scanner);

		System.out.println();
		System.out.println(TABLE_TITLE);

		var result = studentsService.findAllBy(input);

		printAsTableFormatt(result);
	}

	private void printAsTableFormatt(List<StudentDTO> result) {
		System.out.format("%-15s%-15s%-15s%n", "ID", "First name", "Last name");
		for (var dto : result) {
			System.out.format("%-15s%-15s%-15s%n", dto.getId(), dto.getFirstName(), dto.getLastName());
		}
	}
}
