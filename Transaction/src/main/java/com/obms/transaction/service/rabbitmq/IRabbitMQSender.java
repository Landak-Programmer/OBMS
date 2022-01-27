package com.obms.transaction.service.rabbitmq;

import com.obms.transaction.model.Transaction;

public interface IRabbitMQSender {

    void sendTransactionMessageToAccount(final Transaction transaction);
}
