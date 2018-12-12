package com.epam.traning.coins.exceptions;

public class ExpressionCoinsException extends RuntimeException {
    public ExpressionCoinsException(String message) {
        super(message);
    }

    public ExpressionCoinsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExpressionCoinsException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
