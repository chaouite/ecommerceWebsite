package com.swt.fahrradshop.katalog.exceptions;

public class CustomCommandException extends RuntimeException{
    public CustomCommandException(String message) {
        super(message);
    }

    public CustomCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
