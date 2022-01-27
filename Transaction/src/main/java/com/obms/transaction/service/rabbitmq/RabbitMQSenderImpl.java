package com.obms.transaction.service.rabbitmq;

import com.google.gson.Gson;
import com.obms.transaction.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Primary
@Profile("!test")
@Service("RabbitMQSenderImpl")
public class RabbitMQSenderImpl implements IRabbitMQSender {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQSenderImpl.class);

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${app.rabbitmq.account-transaction.exchange}")
    private String exchange;

    @Value("${app.rabbitmq.account-transaction.withdraw.routing-key}")
    private String withdrawKey;

    @Value("${app.rabbitmq.account-transaction.deposit.routing-key}")
    private String depositKey;

    /**
     * Caused by: java.lang.ClassNotFoundException: com.obms.transaction.model.Transaction
     * <p>
     * to be solved by converting object to string 1st before sending message
     *
     * @param transaction object to be send
     */
    @Override
    public void sendTransactionMessageToAccount(final Transaction transaction) {
        final String msg = new Gson().toJson(transaction);
        switch (transaction.getType()) {
            case WITHDRAW:
                rabbitTemplate.convertAndSend(exchange, withdrawKey, msg);
                break;
            case DEPOSIT:
                rabbitTemplate.convertAndSend(exchange, depositKey, msg);
                break;
            default:
                throw new UnsupportedOperationException(String.format("No such operation as %s", transaction.getType()));
        }
        LOGGER.info("Send msg = {}", msg);
    }

}
