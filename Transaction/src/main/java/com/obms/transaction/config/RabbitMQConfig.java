package com.obms.transaction.config;

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

    @Value("${app.rabbitmq.account-transaction.withdraw.routing-key}")
    private String withdrawKey;

    @Value("${app.rabbitmq.account-transaction.deposit.queue}")
    String depositQueue;

    @Value("${app.rabbitmq.account-transaction.transaction-status.queue}")
    String transactionStatusQueue;

    @Value("${app.rabbitmq.account-transaction.deposit.routing-key}")
    private String depositKey;

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
    Binding withdrawalBind(Queue withdrawQueue, DirectExchange exchange) {
        return BindingBuilder.bind(withdrawQueue).to(exchange).with(withdrawKey);
    }

    @Bean
    Binding depositBind(Queue depositQueue, DirectExchange exchange) {
        return BindingBuilder.bind(depositQueue).to(exchange).with(depositKey);
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
        simpleMessageListenerContainer.setQueues(transactionStatusQueue());
        return simpleMessageListenerContainer;

    }

}
