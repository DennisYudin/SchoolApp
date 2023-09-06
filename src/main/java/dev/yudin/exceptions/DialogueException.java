package dev.yudin.exceptions;

public class DialogueException extends RuntimeException {

    public DialogueException() {
        super();
    }

    public DialogueException(String message, Throwable cause) {
        super(message, cause);
    }

    public DialogueException(String message) {
        super(message);
    }
}
