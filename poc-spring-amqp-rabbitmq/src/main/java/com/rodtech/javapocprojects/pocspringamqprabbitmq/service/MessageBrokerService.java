package com.rodtech.javapocprojects.pocspringamqprabbitmq.service;

import com.rodtech.javapocprojects.pocspringamqprabbitmq.dto.TransactionDTO;

public interface MessageBrokerService {
    void consuming(TransactionDTO transactionDTO);
    void sendTransaction(TransactionDTO transactionDTO);
}
