package com.epam.lab.pavel_katsuba.credit_app.exceptions;

public class JsonAdapterException extends RuntimeException {
    public JsonAdapterException(String message) {
        super(message);
    }

    public JsonAdapterException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonAdapterException(Throwable cause) {
        super(cause);
    }
}
