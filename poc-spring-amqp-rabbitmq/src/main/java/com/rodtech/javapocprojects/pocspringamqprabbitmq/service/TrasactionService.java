package com.rodtech.javapocprojects.pocspringamqprabbitmq.service;

import com.rodtech.javapocprojects.pocspringamqprabbitmq.dto.TransactionDTO;

public interface TrasactionService {
    void process(TransactionDTO transactionDTO);
}
