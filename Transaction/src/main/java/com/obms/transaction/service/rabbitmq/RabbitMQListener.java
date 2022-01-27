package com.obms.transaction.service.rabbitmq;

import com.obms.transaction.helper.JsonHelper;
import com.obms.transaction.model.Transaction;
import com.obms.transaction.service.TransactionServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQListener.class);

    @Autowired
    private TransactionServiceImpl transactionService;

    @RabbitListener(queues = "obms.account-transaction.transaction-status")
    public void processCallbackForAccountTransaction(Message message) {
        LOGGER.info("Consuming Message - {} ", new String(message.getBody()));
        final Transaction changeSet = JsonHelper.convertMQMessage(message.getBody(), Transaction.class);
        transactionService.update(changeSet.getId(), changeSet);
    }
}
