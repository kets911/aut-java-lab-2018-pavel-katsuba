package com.epam.lab.pavel_katsuba.credit_app.exceptions;

public class SettingValidateException extends RuntimeException {
    public SettingValidateException(String message) {
        super(message);
    }

    public SettingValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
