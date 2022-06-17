package com.rodtech.javapocprojects.pocspringamqprabbitmq.listener;

import com.rodtech.javapocprojects.pocspringamqprabbitmq.dto.TransactionDTO;
import com.rodtech.javapocprojects.pocspringamqprabbitmq.service.TrasactionService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionListener {

    private final TrasactionService trasactionService;

    public TransactionListener(TrasactionService trasactionService) {
        this.trasactionService = trasactionService;
    }

    @RabbitListener(queues = "${broker.queue.transaction}")
    public void send(TransactionDTO transactionDTO) {
        trasactionService.process(transactionDTO);
    }
}
