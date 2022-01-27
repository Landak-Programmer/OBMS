package com.obms.account.service;

import com.obms.account.exception.InsufficientAmountException;
import com.obms.account.model.JpaEntity;
import com.obms.account.model.external.Transaction;

import java.io.Serializable;
import java.math.BigDecimal;

public interface AccountService<E extends JpaEntity<ID>, ID extends Serializable> extends IService<E, ID> {
    void credit(final Long transactionId, String accNum, BigDecimal amount);

    void deduct(final Long transactionId, String accNum, BigDecimal amount) throws InsufficientAmountException;

    void sendCallbackMessage(Long transactionId, Transaction.Status status);
}