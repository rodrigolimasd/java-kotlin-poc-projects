package com.rodtech.javapocprojects.pocspringamqprabbitmq.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQBroadcastConfig {

    private static final boolean NON_DURABLE = false;

    @Value("${broker.queue.transaction-credit-card}")
    public String TOPIC_QUEUE_CREDIT_CARD_NAME;

    @Value("${broker.queue.transaction-bank}")
    public String TOPIC_QUEUE_BANK_NAME;

    @Value("${broker.queue.transaction}")
    public String TOPIC_QUEUE_TRANSACTION_NAME;

    @Value("${broker.exchange}")
    public String TOPIC_EXCHANGE_NAME;

    public static final String BINDING_PATTERN_CREDIT_CARD = "*.credit-card.*";
    public static final String BINDING_PATTERN_BANK = "*.bank.*";
    public static final String BINDING_PATTERN_GENERAL = "*.general.*";

    @Bean
    public Declarables topicBindings() {
        Queue topicQueueCreditCard = new Queue(TOPIC_QUEUE_CREDIT_CARD_NAME, NON_DURABLE);
        Queue topicQueueBank = new Queue(TOPIC_QUEUE_BANK_NAME, NON_DURABLE);
        Queue topicQueueTransactions = new Queue(TOPIC_QUEUE_TRANSACTION_NAME, NON_DURABLE);

        TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME, NON_DURABLE, false);

        return new Declarables(topicQueueCreditCard, topicQueueBank, topicQueueTransactions, topicExchange,
                BindingBuilder
                    .bind(topicQueueCreditCard)
                    .to(topicExchange)
                    .with(BINDING_PATTERN_CREDIT_CARD),
                BindingBuilder
                    .bind(topicQueueBank)
                    .to(topicExchange)
                    .with(BINDING_PATTERN_BANK),
                BindingBuilder
                    .bind(topicQueueTransactions)
                    .to(topicExchange)
                    .with(BINDING_PATTERN_GENERAL)
        );
    }

    @Bean
    public MessageConverter messageConverter() {
        ObjectMapper mapper = new ObjectMapper().findAndRegisterModules();
        return new Jackson2JsonMessageConverter(mapper);
    }

}
