package com.obms.transaction.service;

import com.obms.transaction.exception.InsufficientArgumentException;
import com.obms.transaction.model.Transaction;
import com.obms.transaction.repository.TransactionRepository;
import com.obms.transaction.service.rabbitmq.IRabbitMQSender;
import com.obms.transaction.service.rabbitmq.RabbitMQSenderImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

import static java.lang.String.format;

@Service
public class TransactionServiceImpl implements TransactionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

    private final TransactionRepository transactionRepository;

    private final IRabbitMQSender rabbitMQSender;

    @Autowired
    public TransactionServiceImpl(
            final TransactionRepository transactionRepository,
            final IRabbitMQSender rabbitMQSender) {
        this.transactionRepository = transactionRepository;
        this.rabbitMQSender = rabbitMQSender;
    }

    @Transactional(readOnly = true)
    @Override
    public Transaction getById(Long id) {
        return transactionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException(format("%s (ID %s) not found", Transaction.class, id)));
    }

    @Transactional
    @Override
    public Transaction update(final Long id, final Transaction changeSet) {
        final Transaction pTransaction = this.getById(id);

        if (pTransaction.getStatus() != changeSet.getStatus()) {
            pTransaction.setStatus(changeSet.getStatus());
        }

        return this.save(pTransaction);
    }

    @Transactional
    @Override
    public Transaction withdraw(final Transaction transaction) {
        validate(transaction);
        transaction.setStatus(Transaction.Status.PENDING);
        transaction.setType(Transaction.Type.WITHDRAW);
        save(transaction);
        rabbitMQSender.sendTransactionMessageToAccount(transaction);
        return transaction;
    }

    @Transactional
    @Override
    public Transaction deposit(final Transaction transaction) {
        validate(transaction);
        transaction.setStatus(Transaction.Status.PENDING);
        transaction.setType(Transaction.Type.DEPOSIT);
        save(transaction);
        rabbitMQSender.sendTransactionMessageToAccount(transaction);
        return transaction;
    }

    @Transactional
    @Override
    public Transaction save(final Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    // -------------------------------------------------- UTIL --------------------------------------------------

    private void validate(final Transaction transaction) {
        failIfBlank(transaction.getAccountNumber(), "accountNumber");
        failIfNull(transaction.getAmount(), "amount");
    }

    // make generic out of this
    private void failIfBlank(final String value, final String args) {
        if (StringUtils.isBlank(value)) {
            throw new InsufficientArgumentException(String.format("%s cannot be null/blank", args));
        }
    }

    private void failIfNull(final Object value, final String args) {
        if (value == null) {
            throw new InsufficientArgumentException(String.format("%s cannot be null", args));
        }
    }

}
