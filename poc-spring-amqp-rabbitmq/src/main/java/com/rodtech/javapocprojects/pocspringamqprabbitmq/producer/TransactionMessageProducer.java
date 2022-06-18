package com.rodtech.javapocprojects.pocspringamqprabbitmq.producer;

import com.rodtech.javapocprojects.pocspringamqprabbitmq.dto.TransactionDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class TransactionMessageProducer {

    @Value("${broker.exchange}")
    public String TOPIC_EXCHANGE_NAME;

    private static String ROUTING_KEY_USER_CREDIT_CARD_MASTER = "user.credit-card.master";
    private static String ROUTING_KEY_USER_BANK_MYBANK = "user.bank.mybank";
    private static String ROUTING_KEY_USER_TRANSACTIONS_ALL = "user.general.others";

    private final AmqpTemplate aqmqpTemplate;

    public TransactionMessageProducer(AmqpTemplate aqmqpTemplate) {
        this.aqmqpTemplate = aqmqpTemplate;
    }

    public void sendCreditCardTransaction(TransactionDTO transactionDTO){
        log.info("producer message with routing key {} ", ROUTING_KEY_USER_CREDIT_CARD_MASTER);
        aqmqpTemplate.convertAndSend(
                TOPIC_EXCHANGE_NAME,
                ROUTING_KEY_USER_CREDIT_CARD_MASTER,
                transactionDTO);
        log.info("message produced");
    }

    public void sendBankTransaction(TransactionDTO transactionDTO){
        log.info("producer message with routing key {} ", ROUTING_KEY_USER_BANK_MYBANK);
        aqmqpTemplate.convertAndSend(
                TOPIC_EXCHANGE_NAME,
                ROUTING_KEY_USER_BANK_MYBANK,
                transactionDTO);
        log.info("message produced");
    }

    public void sendGeneralTransaction(TransactionDTO transactionDTO){
        log.info("producer message with routing key {} ", ROUTING_KEY_USER_TRANSACTIONS_ALL);
        aqmqpTemplate.convertAndSend(
                TOPIC_EXCHANGE_NAME,
                ROUTING_KEY_USER_TRANSACTIONS_ALL,
                transactionDTO);
        log.info("message produced");
    }
}
