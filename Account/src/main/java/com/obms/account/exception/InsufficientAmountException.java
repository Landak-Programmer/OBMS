package com.obms.account.exception;

/**
 * Maybe it should extend runtime ex?
 */
public class InsufficientAmountException extends Exception {

    public InsufficientAmountException(final String accNum, final String amount) {
        super(String.format("Unable to deduct from acc %s with amount %s due to insufficient balance", accNum, accNum));
    }
}
