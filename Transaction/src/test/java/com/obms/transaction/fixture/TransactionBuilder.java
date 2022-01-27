package com.obms.transaction.fixture;

import com.obms.transaction.model.Transaction;
import org.apache.commons.lang3.RandomStringUtils;

import java.math.BigDecimal;

public class TransactionBuilder {

    private String accountNumber = RandomStringUtils
            .randomNumeric(16);
    private BigDecimal amount;

    public static TransactionBuilder sample() {
        return new TransactionBuilder();
    }

    public Transaction build() {
        final Transaction transaction = new Transaction();
        transaction.setAccountNumber(accountNumber);

        if (amount == null) {
            amount = new BigDecimal("1000");
        }
        transaction.setAmount(amount);
        return transaction;
    }

    public TransactionBuilder withAmount(final String amount) {
        this.amount = new BigDecimal(amount);
        return this;
    }
}
