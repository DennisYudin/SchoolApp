package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.console.InputHandler;
import dev.yudin.entities.GroupsAmountStudentDTO;
import dev.yudin.services.GroupsService;

import java.util.List;
import java.util.Scanner;

public class GroupsDialogue implements Dialogue {
	private static final String STUDENTS_MESSAGE = "Enter amount of students: ";
	public static final String TABLE_TITLE = "Groups table:";
	private Console inputHandler;
	private GroupsService groupsService;

	public GroupsDialogue(Console inputHandler, GroupsService groupsService) {
		this.inputHandler = inputHandler;
		this.groupsService = groupsService;
	}

	@Override
	public void start(Scanner scanner) {
		int amountStudents = inputHandler.readInt(STUDENTS_MESSAGE, scanner);
		scanner.nextLine();

		System.out.println();
		System.out.println(TABLE_TITLE);

		var result = groupsService.findAll(amountStudents);

		printAsTableFormatt(result);
	}

	private void printAsTableFormatt(List<GroupsAmountStudentDTO> result) {
		System.out.format("%-15s%-15s%n", "Group name", "Amount of students");
		for (var dto : result) {
			System.out.format("%-15s%-15s%n", dto.getGroupName(), dto.getAmountStudents());
		}
	}
}
