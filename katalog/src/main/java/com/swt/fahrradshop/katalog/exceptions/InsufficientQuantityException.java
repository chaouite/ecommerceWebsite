package com.swt.fahrradshop.katalog.exceptions;

import lombok.Data;

@Data
public class InsufficientQuantityException extends RuntimeException{
    public InsufficientQuantityException(String message) {
        super(message);
    }

    public InsufficientQuantityException(String message, Throwable cause) {
        super(message, cause);
    }

}
