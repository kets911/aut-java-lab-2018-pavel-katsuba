package com.epam.lab.pavel_katsuba.credit_app.exceptions;

public class DBManagerException extends RuntimeException {
    public DBManagerException(String message) {
        super(message);
    }

    public DBManagerException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBManagerException(Throwable cause) {
        super(cause);
    }
}
