package com.epam.lab.pavel_katsuba.vneklasniki.exceptions;

public class DataBaseException extends RuntimeException {
    public DataBaseException(String message) {
        super(message);
    }

    public DataBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataBaseException(Throwable cause) {
        super(cause);
    }
}
