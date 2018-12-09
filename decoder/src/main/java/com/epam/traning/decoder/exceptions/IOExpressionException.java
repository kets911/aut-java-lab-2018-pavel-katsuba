package com.epam.traning.decoder.exceptions;

public class IOExpressionException extends RuntimeException {
    public IOExpressionException(String message) {
        super(message);
    }

    public IOExpressionException(String message, Throwable cause) {
        super(message, cause);
    }

    public IOExpressionException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
