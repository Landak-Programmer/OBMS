package com.obms.transaction.service;

import com.obms.transaction.model.Transaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface TransactionService {

    Transaction getById(final Long uuid);

    @Transactional
    Transaction update(Long id, Transaction changeSet);

    Transaction withdraw(Transaction transaction);

    Transaction deposit(Transaction transaction);

    Transaction save(Transaction transaction);
}
