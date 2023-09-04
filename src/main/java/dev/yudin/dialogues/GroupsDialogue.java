package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.services.GroupsService;

import java.util.Scanner;

public class GroupsDialogue implements Dialogue {
	private static final String STUDENTS_MESSAGE = "Enter amount of students: ";
	private Console console;
	private GroupsService groupsService;

	public GroupsDialogue(Console console, GroupsService groupsService) {
		this.console = console;
		this.groupsService = groupsService;
	}

	@Override
	public void start(Scanner scanner) {
		int amountStudents = console.read(STUDENTS_MESSAGE, scanner);
		scanner.nextLine();

		System.out.println();
		System.out.println("List of groups:");

		var result = groupsService.findAll(amountStudents);
		result.forEach(System.out::println);
	}
}
