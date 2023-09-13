package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.entities.StudentDTO;
import dev.yudin.services.StudentsService;

import java.util.List;
import java.util.Scanner;

public class FindAllStudentsDialogue implements Dialogue {
	public static final String TABLE_TITLE = "Students table:";
	public static final String COURSE_MESSAGE = "Enter course name: ";
	private Console inputHandler;
	private StudentsService studentsService;

	public FindAllStudentsDialogue(Console inputHandler, StudentsService studentsService) {
		this.inputHandler = inputHandler;
		this.studentsService = studentsService;
	}

	@Override
	public void start(Scanner scanner) {
		var input = inputHandler.readString(COURSE_MESSAGE, scanner);

		System.out.println();
		System.out.println(TABLE_TITLE);

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
