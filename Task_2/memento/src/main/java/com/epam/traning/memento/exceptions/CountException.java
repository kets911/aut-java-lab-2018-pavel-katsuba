package com.epam.traning.memento.exceptions;

public class CountException extends RuntimeException {
    public CountException(String message) {
        super(message);
    }

    public CountException(String message, Throwable cause) {
        super(message, cause);
    }

    public CountException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
