package com.obms.account.service;

import com.obms.account.exception.InactivateAccountException;
import com.obms.account.model.Account;
import com.obms.account.model.external.Transaction;
import com.obms.account.repository.AccountRepository;
import com.obms.account.service.rabbitmq.RabbitMQSender;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class AccountServiceImpl extends BaseEntityService<Account, Long> implements AccountService<Account, Long> {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Override
    protected PagingAndSortingRepository<Account, Long> getRepository() {
        return accountRepository;
    }

    /**
     * @param entity
     */
    @Override
    protected void preCreate(final Account entity) {
        // not null annotation should do this for us already
        failIfBlank(entity.getAccountOwner(), "accountOwner");

        entity.setAmount(BigDecimal.ZERO);
        entity.setStatus(Account.Status.ACTIVE);

        // naive
        String accountNumber;
        do {
            accountNumber = RandomStringUtils
                    .randomNumeric(16);
        } while (accountRepository.existsByAccountNumber(accountNumber));
        entity.setAccountNumber(accountNumber);

    }

    /**
     * We are only able to update name of owner and status
     * <p>
     * Amount will have dedicated method for it
     *
     * @param pEntity
     * @param changeSet
     */
    @Override
    protected void preUpdate(final Account pEntity, final Account changeSet) {

        if (isChanged(pEntity.getAccountOwner(), changeSet.getAccountOwner())) {
            pEntity.setAccountOwner(changeSet.getAccountOwner());
        }

        if (isChanged(pEntity.getStatus(), changeSet.getStatus())) {
            pEntity.setStatus(changeSet.getStatus());
        }
    }

    @Transactional
    @Override
    public void credit(final Long transactionId, final String accNum, final BigDecimal amount) {
        final Account account = accountRepository.findByAccountNumber(accNum);
        failIfInactive(account.getAccountNumber(), account.getStatus());
        account.setAmount(account.getAmount().add(amount));
        accountRepository.save(account);

        this.sendCallbackMessage(transactionId, Transaction.Status.SUCCESS);
    }

    @Transactional
    @Override
    public void deduct(final Long transactionId, final String accNum, final BigDecimal amount) {

        final Account account = accountRepository.findByAccountNumber(accNum);
        failIfInactive(account.getAccountNumber(), account.getStatus());

        if (account.getAmount().compareTo(amount) < 0) {
            // todo: should throw an exception here
            // todo: maybe we want to create log for the error
            this.sendCallbackMessage(transactionId, Transaction.Status.FAILED);
        }
        account.setAmount(account.getAmount().subtract(amount));
        accountRepository.save(account);

        this.sendCallbackMessage(transactionId, Transaction.Status.SUCCESS);
    }

    @Override
    public void sendCallbackMessage(final Long transactionId, Transaction.Status status) {
        final Transaction response = new Transaction();
        response.setId(transactionId);
        response.setStatus(status);
        rabbitMQSender.sendCallbackToUpdateStatus(response);
    }

    // ----------------------------------------- UTIL -----------------------------------------
    private void failIfInactive(final String accNum, final Account.Status status) {
        if (status == Account.Status.DEACTIVATE) {
            throw new InactivateAccountException(accNum);
        }
    }
}
