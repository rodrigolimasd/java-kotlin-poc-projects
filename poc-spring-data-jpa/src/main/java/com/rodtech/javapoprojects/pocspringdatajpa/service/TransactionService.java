package com.rodtech.javapoprojects.pocspringdatajpa.service;

import com.rodtech.javapoprojects.pocspringdatajpa.dto.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    TransactionDTO create(TransactionDTO transaction);
    TransactionDTO getById(Long id);
    TransactionDTO update(TransactionDTO transaction);
    Page<TransactionDTO> list(Pageable page);
    void delete(Long id);
}
