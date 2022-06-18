package com.rodtech.javapocprojects.pocspringamqprabbitmq.service.impl;

import com.rodtech.javapocprojects.pocspringamqprabbitmq.dto.TransactionDTO;
import com.rodtech.javapocprojects.pocspringamqprabbitmq.dto.TransactionTypeEnum;
import com.rodtech.javapocprojects.pocspringamqprabbitmq.producer.TransactionMessageProducer;
import com.rodtech.javapocprojects.pocspringamqprabbitmq.service.MessageBrokerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class MessageBrokerServiceImpl implements MessageBrokerService {

    private final TransactionMessageProducer transactionMessageProducer;

    public MessageBrokerServiceImpl(TransactionMessageProducer transactionMessageProducer) {
        this.transactionMessageProducer = transactionMessageProducer;
    }

    @Override
    public void consuming(TransactionDTO transactionDTO) {
        log.info("Consumed Message {} ", transactionDTO.toString());
    }

    @Override
    public void sendTransaction(TransactionDTO transactionDTO) {
        if(transactionDTO.getType()!=null &&
                transactionDTO.getType().equals(TransactionTypeEnum.BANK.name())){
            transactionMessageProducer.sendBankTransaction(transactionDTO);
        } else if(transactionDTO.getType()!=null &&
                transactionDTO.getType().equals(TransactionTypeEnum.CREDIT_CARD.name())){
            transactionMessageProducer.sendCreditCardTransaction(transactionDTO);
        } else {
           transactionMessageProducer.sendGeneralTransaction(transactionDTO);
        }
    }
}
