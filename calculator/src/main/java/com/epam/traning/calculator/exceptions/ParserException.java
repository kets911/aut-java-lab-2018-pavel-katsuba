package com.epam.traning.calculator.exceptions;

import com.epam.traning.calculator.Constants;

public class ParserException extends CalculatorException {
    private String wrongLine;

    public ParserException(String message, String wrongLine) {
        super(message);
        this.wrongLine = wrongLine;
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return getMessage() + Constants.ERROR_EXCEPTION_DELIMITER + wrongLine;
    }
}
