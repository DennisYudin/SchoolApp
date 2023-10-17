package dev.yudin.dialogues;

import dev.yudin.console.Console;
import dev.yudin.entities.Student;
import dev.yudin.services.StudentsService;

import java.util.Scanner;

public class AddExistStudentToNewCourseDialogue implements Dialogue {
	private Console inputHandler;
	private StudentsService studentsService;
	public AddExistStudentToNewCourseDialogue(Console inputHandler,
											  StudentsService studentsService) {
		this.inputHandler = inputHandler;
		this.studentsService = studentsService;
	}

	@Override
	public void start(Scanner scanner) {

		Student studentInput = new Student();
		studentInput.setFirstName("Dennis");
		studentInput.setFirstName("Yudin");
	}
}
