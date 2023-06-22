package dev.yudin.exceptions;

public class ConnectionException extends RuntimeException {
    
    public ConnectionException() {
        super();
    }
    
    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public ConnectionException(String message) {
        super(message);
    }
}

