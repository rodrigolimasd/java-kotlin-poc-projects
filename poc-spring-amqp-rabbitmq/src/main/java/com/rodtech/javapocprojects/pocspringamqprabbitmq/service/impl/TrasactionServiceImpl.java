package com.rodtech.javapocprojects.pocspringamqprabbitmq.service.impl;

import com.rodtech.javapocprojects.pocspringamqprabbitmq.dto.TransactionDTO;
import com.rodtech.javapocprojects.pocspringamqprabbitmq.dto.TransactionTypeEnum;
import com.rodtech.javapocprojects.pocspringamqprabbitmq.producer.TransactionMessageProducer;
import com.rodtech.javapocprojects.pocspringamqprabbitmq.service.TrasactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TrasactionServiceImpl implements TrasactionService {

    private final TransactionMessageProducer transactionMessageProducer;

    public TrasactionServiceImpl(TransactionMessageProducer transactionMessageProducer) {
        this.transactionMessageProducer = transactionMessageProducer;
    }

    @Override
    public void process(TransactionDTO transactionDTO) {
        log.info("Received {} ", transactionDTO.toString());
    }

    @Override
    public void sendTransaction(TransactionDTO transactionDTO) {
        if(transactionDTO.getType().equals(TransactionTypeEnum.BANK.name())){
            transactionMessageProducer.sendBankTransaction(transactionDTO);
        } else if(transactionDTO.getType().equals(TransactionTypeEnum.CREDIT_CARD.name())){
            transactionMessageProducer.sendCreditCardTransaction(transactionDTO);
        } else {
            log.error("message error");
            throw new RuntimeException("Type not informed");
        }
    }
}
