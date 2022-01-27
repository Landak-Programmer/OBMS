package com.obms.transaction.exception;

public class InsufficientArgumentException extends IllegalArgumentException {

    public InsufficientArgumentException(final String args) {
        super(String.format("%s is required", args));
    }
}
