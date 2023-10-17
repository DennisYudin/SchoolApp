package dev.yudin.dialogues;

import dev.yudin.connection.ConnectionManager;
import dev.yudin.connection.Manager;
import dev.yudin.console.Console;
import dev.yudin.console.InputHandler;
import dev.yudin.dao.CourseDAO;
import dev.yudin.dao.GroupDAO;
import dev.yudin.dao.StudentDAO;
import dev.yudin.dao.impl.CoursesDAOImpl;
import dev.yudin.dao.impl.GroupsDAOImpl;
import dev.yudin.dao.impl.StudentsDAOImpl;
import dev.yudin.filereader.FileReader;
import dev.yudin.filereader.Reader;
import dev.yudin.services.CoursesService;
import dev.yudin.services.GroupsService;
import dev.yudin.services.StudentsService;
import dev.yudin.services.impl.CourseServiceImpl;
import dev.yudin.services.impl.GroupsServiceImpl;
import dev.yudin.services.impl.StudentsServiceImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class InitDialogue implements Dialogue {
    private static final String START_DIALOGUE_MESSAGE = "Please choose a letter what do you want to do: \n"
            + "'a' if you want to FIND ALL GROUPS with less or equals student count \n"
            + "'b' if you want to FIND ALL STUDENTS related to course with given name \n"
            + "'c' if you want to ADD NEW STUDENT\n"
            + "'d' if you want to DELETE STUDENT by STUDENT_ID\n"
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
    private Reader reader = new FileReader();
    Manager dataSource = new ConnectionManager(reader);
    GroupDAO groupDAO = new GroupsDAOImpl(dataSource);
    GroupsService groupsService = new GroupsServiceImpl(groupDAO);
    Console inputHandler = new InputHandler();
    StudentDAO studentDAO = new StudentsDAOImpl(dataSource);
    StudentsService studentsService = new StudentsServiceImpl(studentDAO);
    CourseDAO courseDAO = new CoursesDAOImpl(dataSource);
    CoursesService coursesService = new CourseServiceImpl(courseDAO);

    private Dialogue findAllGroupsDialogue = new FindAllGroupsDialogue(inputHandler, groupsService);
    private Dialogue findAllStudentsDialogue = new FindAllStudentsDialogue(inputHandler, studentsService, coursesService);
    private Dialogue addNewStudentDialogue = new AddNewStudentDialogue(inputHandler, studentsService);
    private Dialogue deleteStudentByIdDialogue = new DeleteStudentByIdDialogue(inputHandler, studentsService);
    private Map<String, Dialogue> dialogs = new HashMap<>();

    @Override
    public void start(Scanner scanner) {
        String userAnswer;
        do {
            System.out.println();
            System.out.print(START_DIALOGUE_MESSAGE);

            initAllDialogues();

            startDialogue(scanner);

            System.out.println(REPEAT_MESSAGE);
            System.out.print(USER_ANSWER);

            userAnswer = scanner.nextLine().toLowerCase();
            System.out.println();

        } while (CONTINUE_ANSWER.equals(userAnswer));
    }

    private void initAllDialogues() {
        dialogs.put("a", findAllGroupsDialogue);
        dialogs.put("b", findAllStudentsDialogue);
        dialogs.put("c", addNewStudentDialogue);
        dialogs.put("d", deleteStudentByIdDialogue);
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
