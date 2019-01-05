package com.epam.lab.pavel_katsuba.collection.exceptions;

public class EmptyCollectionException extends RuntimeException {
    public EmptyCollectionException() {
    }

    public EmptyCollectionException(String message) {
        super(message);
    }

    public EmptyCollectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
