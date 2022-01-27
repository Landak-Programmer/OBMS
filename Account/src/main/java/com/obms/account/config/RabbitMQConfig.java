package com.obms.account.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("!test")
@Configuration
public class RabbitMQConfig {

    @Value("${app.rabbitmq.account-transaction.exchange}")
    String exchange;

    @Value("${app.rabbitmq.account-transaction.withdraw.queue}")
    String withdrawQueue;

    @Value("${app.rabbitmq.account-transaction.deposit.queue}")
    String depositQueue;

    @Value("${app.rabbitmq.account-transaction.transaction-status.queue}")
    String transactionStatusQueue;

    @Value("${app.rabbitmq.account-transaction.transaction-status.routing-key}")
    private String transactionStatusKey;


    @Bean
    DirectExchange exchange() {
        return new DirectExchange(exchange);
    }

    @Bean
    Queue withdrawQueue() {
        return new Queue(withdrawQueue, false);
    }

    @Bean
    Queue depositQueue() {
        return new Queue(depositQueue, false);
    }

    @Bean
    Queue transactionStatusQueue() {
        return new Queue(transactionStatusQueue, false);
    }

    @Bean
    Binding transactionStatusBind(Queue transactionStatusQueue, DirectExchange exchange) {
        return BindingBuilder.bind(transactionStatusQueue).to(exchange).with(transactionStatusKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    MessageListenerContainer messageListenerContainer(ConnectionFactory connectionFactory ) {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer();
        simpleMessageListenerContainer.setConnectionFactory(connectionFactory);
        simpleMessageListenerContainer.setQueues(withdrawQueue());
        simpleMessageListenerContainer.setQueues(depositQueue());
        return simpleMessageListenerContainer;

    }

}
