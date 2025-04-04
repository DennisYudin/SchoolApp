package dev.yudin;

import dev.yudin.dialogues.Dialogue;
import dev.yudin.dialogues.InitDialogue;

import java.util.Scanner;

public class SchoolApp {

	public static void main(String[] args) {
		DBInitializer dbInitializer = new DBInitializer();
		Dialogue initDialogue = new InitDialogue();

		try (Scanner scanner = new Scanner(System.in)){
			dbInitializer.init();
			initDialogue.start(scanner);
		} catch (Exception ex) {
			System.err.println(ex.getMessage());
		}
	}
}
