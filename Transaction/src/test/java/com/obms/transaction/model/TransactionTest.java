package com.obms.transaction.model;

import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * FIXME: Redundant maintenance
 */
@Getter
public class TransactionTest {

    @NotNull
    private Long id;

    @NotNull
    private Transaction.Type type;

    @NotNull
    private Transaction.Status status;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String accountNumber;

    public Transaction toTransaction() {
        final Transaction transaction = new Transaction();
        transaction.setId(id);
        transaction.setType(type);
        transaction.setStatus(status);
        transaction.setAmount(amount);
        transaction.setAccountNumber(accountNumber);
        return transaction;
    }
}
