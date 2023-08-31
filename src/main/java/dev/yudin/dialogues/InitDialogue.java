package dev.yudin.dialogues;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.Manager;
import dev.yudin.console.Console;
import dev.yudin.dao.GroupDAO;
import dev.yudin.dao.impl.GroupsDAOImpl;
import dev.yudin.services.GroupsService;
import dev.yudin.services.impl.GroupsServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class InitDialogue implements Dialogue {
    private static final String START_DIALOGUE_MESSAGE = "Please choose a letter what do you want to do: \n"
            + "'a' if you want to FIND ALL GROUPS with less or equals student count \n"
            + "'b' if you want to find all students related to course with given name \n"
            + "'c' if you want to add new student\n"
            + "'d' if you want to delete student by STUDENT_ID\n"
            + "'e' if you want to add a student to the course (from a list)\n"
            + "'f' if you want to remove the student from one of his or her courses\n "
            + "----------------------------------------------------------------------\n"
            + "Your choice: ";
    private static final String USER_INPUT_MESSAGE = "Enter letter from a to f: ";
    private static final String REPEAT_MESSAGE = "Enter [yes] if you want to try again";
    private static final String USER_ANSWER = "Answer: ";
    private static final String INCORRECT_INPUT_MESSAGE = "Incorrect input. ";
    private static final String ERROR_MESSAGE = "Unfortunately the number of attempts exceeded";
    public static final String CONTINUE_ANSWER = "yes";
    Manager dataSource = new ConnectionManager();
    GroupDAO groupDAO = new GroupsDAOImpl(dataSource);
    GroupsService groupsService = new GroupsServiceImpl(groupDAO);
    Console console = new Console();
    private Dialogue groupsDialogue = new GroupsDialogue(console, groupsService);
    private Map<String, Dialogue> dialogs = new HashMap<>();

    @Override
    public void start(Scanner scanner) {
        String userAnswer;
        do {
            System.out.print(START_DIALOGUE_MESSAGE);

            initDialogues();

            startDialogue(scanner);

            System.out.println(REPEAT_MESSAGE);
            System.out.print(USER_ANSWER);

            userAnswer = scanner.nextLine().toLowerCase();
            System.out.println();

        } while (CONTINUE_ANSWER.equals(userAnswer));
    }

    private void initDialogues() {
        dialogs.put("a", groupsDialogue);
    }

    private void startDialogue(Scanner scanner) {

        String userInput = getAndValidateUserAnswer(scanner);

        Dialogue dialogue = dialogs.get(userInput);
        dialogue.start(scanner);
    }

    private String getAndValidateUserAnswer(Scanner scanner) {
        List<String> options = new ArrayList<>(dialogs.keySet());

        int counter = 0;
        String userInput;
        do {
            System.out.print(USER_INPUT_MESSAGE);

            userInput = scanner.nextLine().toLowerCase();

            if (!options.contains(userInput)) {
                counter++;
                System.out.println(INCORRECT_INPUT_MESSAGE);
            }
            if (counter == 5) {
                throw new IllegalArgumentException(ERROR_MESSAGE);
            }
        } while (!options.contains(userInput));

        return userInput;
    }
}