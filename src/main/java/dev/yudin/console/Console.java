package dev.yudin.console;

import java.util.Scanner;

public class Console {
    private static final String ERROR_MESSAGE = "Amount cannot be less or equals zero";
    private static final String INCORRECT_INPUT_MESSAGE = "Incorrect input. ";

    public int read(String prompt, Scanner scanner) {
        int userInput;
        boolean isGoodInput = false;
        do {
            System.out.print(prompt);
            userInput = scanner.nextInt();
            System.out.println(userInput);
            try {
                validate(userInput);
                isGoodInput = true;
            } catch (IllegalArgumentException ex) {
                System.out.println(INCORRECT_INPUT_MESSAGE + ex.getMessage());
            }
        } while (!isGoodInput);

        return userInput;
    }

    private void validate(int input) {
        if (input <= 0) {
            throw new IllegalArgumentException(ERROR_MESSAGE);
        }
    }
}
