package com.swt.fahrradshop.katalog.exceptions;

public class ProduktUnavailableException extends RuntimeException{
    public ProduktUnavailableException(String message) {
        super(message);
    }

    public ProduktUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
