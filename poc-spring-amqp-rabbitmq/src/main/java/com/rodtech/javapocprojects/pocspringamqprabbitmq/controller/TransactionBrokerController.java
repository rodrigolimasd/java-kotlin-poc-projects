package com.rodtech.javapocprojects.pocspringamqprabbitmq.controller;

import com.rodtech.javapocprojects.pocspringamqprabbitmq.dto.TransactionDTO;
import com.rodtech.javapocprojects.pocspringamqprabbitmq.service.TrasactionService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Log4j2
@RestController
@RequestMapping("/v1/transaction")
public class TransactionBrokerController {

    private final TrasactionService trasactionService;

    public TransactionBrokerController(TrasactionService trasactionService) {
        this.trasactionService = trasactionService;
    }

    @PostMapping
    public ResponseEntity<TransactionDTO> create(@RequestBody @Valid TransactionDTO transactionDTO){
        log.info("process transaction {} ", transactionDTO);
        trasactionService.sendTransaction(transactionDTO);
        log.info("transaction processed {} ", transactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(transactionDTO);
    }
}
