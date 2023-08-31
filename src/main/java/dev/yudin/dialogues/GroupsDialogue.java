package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.services.GroupsService;

import java.util.Scanner;

public class GroupsDialogue implements Dialogue {
	private static final String STUDENTS_MESSAGE = "Enter amount of students: ";
	private static final String REPEAT_MESSAGE = "Do you wanna to try again? [yes/no]";
	private static final String USER_ANSWER = "Answer: ";
	private static final String CONTINUE_ANSWER = "yes";
	private Console console;
	private GroupsService groupsService;

	public GroupsDialogue(Console console, GroupsService groupsService) {
		this.console = console;
		this.groupsService = groupsService;
	}

	@Override
	public void start(Scanner scanner) {
		int amountStudents = console.read(STUDENTS_MESSAGE, scanner);

		var result = groupsService.findAll(amountStudents);
		result.forEach(System.out::println);
	}
}
