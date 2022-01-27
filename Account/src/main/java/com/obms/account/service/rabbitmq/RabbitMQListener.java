package com.obms.account.service.rabbitmq;

import com.obms.account.helper.JsonHelper;
import com.obms.account.model.external.Transaction;
import com.obms.account.service.AccountServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * todo: reconfigure listener to remove from queue/go to different queue if exception is thrown
 */
@Service
public class RabbitMQListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQListener.class);

    @Autowired
    private AccountServiceImpl accountService;

    @RabbitListener(queues = "obms.account-transaction.withdraw")
    public void processWithdrawMessage(Message message) {
        LOGGER.info("Consuming Message - {} ", new String(message.getBody()));
        final Transaction transaction = JsonHelper.convertMQMessage(message.getBody(), Transaction.class);
        accountService.deduct(transaction.getId(), transaction.getAccountNumber(), transaction.getAmount());
    }

    @RabbitListener(queues = "obms.account-transaction.deposit")
    public void processDepositMessage(Message message) {
        LOGGER.info("Consuming Message - {} ", new String(message.getBody()));
        final Transaction transaction = JsonHelper.convertMQMessage(message.getBody(), Transaction.class);
        accountService.credit(transaction.getId(), transaction.getAccountNumber(), transaction.getAmount());
    }

}
