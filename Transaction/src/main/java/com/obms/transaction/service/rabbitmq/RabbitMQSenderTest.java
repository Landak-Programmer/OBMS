package com.obms.transaction.service.rabbitmq;

import com.google.gson.Gson;
import com.obms.transaction.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Profile("test")
@Service("RabbitMQSenderTest")
public class RabbitMQSenderTest implements IRabbitMQSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSenderTest.class);

    @Override
    public void sendTransactionMessageToAccount(Transaction transaction) {
        LOGGER.info("Send msg = {}", new Gson().toJson(transaction));
    }
}
