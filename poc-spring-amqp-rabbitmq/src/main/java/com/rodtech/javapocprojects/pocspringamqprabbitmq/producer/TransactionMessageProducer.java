package com.rodtech.javapocprojects.pocspringamqprabbitmq.producer;

import com.rodtech.javapocprojects.pocspringamqprabbitmq.dto.TransactionDTO;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TransactionMessageProducer {

    @Value("${broker.exchange}")
    public String TOPIC_EXCHANGE_NAME;

    private static String ROUTING_KEY_USER_CREDIT_CARD_MASTER = "user.credit-card.master";
    private static String ROUTING_KEY_USER_BANK_MYBANK = "user.bank.mybank";

    private final AmqpTemplate aqmqpTemplate;

    public TransactionMessageProducer(AmqpTemplate aqmqpTemplate) {
        this.aqmqpTemplate = aqmqpTemplate;
    }

    public void sendCreditCardTransaction(TransactionDTO transactionDTO){
        aqmqpTemplate.convertAndSend(
                TOPIC_EXCHANGE_NAME,
                ROUTING_KEY_USER_CREDIT_CARD_MASTER,
                transactionDTO);
    }

    public void sendBankTransaction(TransactionDTO transactionDTO){
        aqmqpTemplate.convertAndSend(
                TOPIC_EXCHANGE_NAME,
                ROUTING_KEY_USER_BANK_MYBANK,
                transactionDTO);
    }
}
