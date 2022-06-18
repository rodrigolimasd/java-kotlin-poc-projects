package com.rodtech.javapocprojects.pocspringamqprabbitmq.consumer;

import com.rodtech.javapocprojects.pocspringamqprabbitmq.dto.TransactionDTO;
import com.rodtech.javapocprojects.pocspringamqprabbitmq.service.MessageBrokerService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionMessageConsumer {

    private final MessageBrokerService messageBrokerService;

    public TransactionMessageConsumer(MessageBrokerService messageBrokerService) {
        this.messageBrokerService = messageBrokerService;
    }

    @RabbitListener(queues = "${broker.queue.transaction}")
    public void send(TransactionDTO transactionDTO) {
        messageBrokerService.consuming(transactionDTO);
    }
}
