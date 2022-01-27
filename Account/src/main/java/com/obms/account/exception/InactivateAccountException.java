package com.obms.account.exception;

public class InactivateAccountException extends IllegalStateException {

    public InactivateAccountException(final String accNum) {
        super(String.format("Account %s is inactive", accNum));
    }
}
