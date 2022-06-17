package com.rodtech.javapocprojects.pocspringamqprabbitmq.service.impl;

import com.rodtech.javapocprojects.pocspringamqprabbitmq.dto.TransactionDTO;
import com.rodtech.javapocprojects.pocspringamqprabbitmq.service.TrasactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class TrasactionServiceImpl implements TrasactionService {
    @Override
    public void process(TransactionDTO transactionDTO) {
        log.info("Received {} ", transactionDTO.toString());
    }
}
