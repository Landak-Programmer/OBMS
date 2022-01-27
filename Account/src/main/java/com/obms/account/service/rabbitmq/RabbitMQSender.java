package com.obms.account.service.rabbitmq;

import com.google.gson.Gson;
import com.obms.account.model.external.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSender.class);

    @Value("${app.rabbitmq.account-transaction.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.account-transaction.transaction-status.routing-key}")
    private String transactionStatusKey;

    @Autowired
    private AmqpTemplate rabbitTemplate;

    public void sendCallbackToUpdateStatus(final Transaction transaction) {
        final String msg = new Gson().toJson(transaction);
        LOGGER.info("Send msg = {}", msg);
        rabbitTemplate.convertAndSend(exchange, transactionStatusKey, msg);
    }
}
