package dev.yudin.console;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.Scanner;

public class InputHandler implements Console {
    private final Logger log = LogManager.getLogger(InputHandler.class);
    private static final String ERROR_MESSAGE = "Amount cannot be less or equals zero";
    private static final String INCORRECT_INPUT_MESSAGE = "Incorrect input. ";
    private static final String INPUT_NULL_MESSAGE = "Input cannot be null";
    private static final String INPUT_EMPTY_MESSAGE = "Input cannot be empty";

    @Override
    public int readInt(String prompt, Scanner scanner) {
        int userInput;
        boolean isGoodInput = false;
        do {
            System.out.print(prompt);
            userInput = scanner.nextInt();
            try {
                validate(userInput);
                isGoodInput = true;
            } catch (IllegalArgumentException ex) {
                log.error(INCORRECT_INPUT_MESSAGE + ex.getMessage());
                System.out.println(INCORRECT_INPUT_MESSAGE + ex.getMessage());
            }
        } while (!isGoodInput);

        return userInput;
    }

    private void validate(int input) {
        if (input <= 0) {
            log.error(ERROR_MESSAGE);
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }

    @Override
    public String readString(String prompt, Scanner scanner) {
        String userInput;
        boolean isGoodInput = false;
        do {
            System.out.print(prompt);

            userInput = scanner.nextLine();
            try {
                validateByNullOrEmpty(userInput);
                isGoodInput = true;
            } catch (IllegalArgumentException ex) {
                log.error(INCORRECT_INPUT_MESSAGE + ex.getMessage());
                System.out.println(INCORRECT_INPUT_MESSAGE + ex.getMessage());
            }
        } while (!isGoodInput);

        return userInput;
    }

    private void validateByNullOrEmpty(String input) {
        if (input == null) {
            log.error(INPUT_NULL_MESSAGE);
            throw new IllegalArgumentException(INPUT_NULL_MESSAGE);
        }
        if (input.trim().isEmpty()) {
            log.error(INPUT_EMPTY_MESSAGE);
            throw new IllegalArgumentException(INPUT_EMPTY_MESSAGE);
        }
    }
}
