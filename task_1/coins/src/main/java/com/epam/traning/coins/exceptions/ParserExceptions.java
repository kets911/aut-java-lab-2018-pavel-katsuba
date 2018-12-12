package com.epam.traning.coins.exceptions;

public class ParserExceptions extends RuntimeException {
    public ParserExceptions(String message) {
        super(message);
    }

    public ParserExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserExceptions(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
