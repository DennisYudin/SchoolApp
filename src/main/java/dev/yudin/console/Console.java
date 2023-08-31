package dev.yudin.console;

import java.util.Scanner;

public class Console {
    private static final String SEPARATOR = ",";
    private static final String REPLACEMENT_SYMBOL = "";
    private static final String INPUT_NULL_MESSAGE = "Input cannot be null";
    private static final String INPUT_EMPTY_MESSAGE = "Input cannot be empty";
    private static final String ERROR_MESSAGE = "Amount cannot be less or equals zero";
    private static final String INCORRECT_INPUT_MESSAGE = "Incorrect input. ";

    public int read(String prompt, Scanner scanner) {
        int userInput;
        boolean isGoodInput = false;
        do {
            System.out.print(prompt);
            userInput = scanner.nextInt();
            try {
                validate(userInput);
                isGoodInput = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(INCORRECT_INPUT_MESSAGE + ex.getMessage());
            }
        } while (!isGoodInput);
        return userInput;
    }

    private String[] getBanknotes(String input) {

        validateByNullOrEmpty(input);

        String inputWithoutSpaces = input.replaceAll("\\s", REPLACEMENT_SYMBOL);

        String[] banknotesStringArray = inputWithoutSpaces.split(SEPARATOR);

        return banknotesStringArray;
    }

    private void validateByNullOrEmpty(String input) {
        if (input == null)
            throw new IllegalArgumentException(INPUT_NULL_MESSAGE);
        if (input.trim().isEmpty()) {
            throw new IllegalArgumentException(INPUT_EMPTY_MESSAGE);
        }
    }

    private void validate(int input) {
        if (input <= 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}

