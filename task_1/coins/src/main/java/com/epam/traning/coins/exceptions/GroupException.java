package com.epam.traning.coins.exceptions;

public class GroupException extends RuntimeException {
    public GroupException(String message) {
        super(message);
    }

    public GroupException(String message, Throwable cause) {
        super(message, cause);
    }

    public GroupException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
