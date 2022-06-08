package com.rodtech.javapoprojects.pocspringdatajpa.service;

import com.rodtech.javapoprojects.pocspringdatajpa.dto.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface TransactionService {
    TransactionDTO create(TransactionDTO transaction);
    List<TransactionDTO> createBatch(List<TransactionDTO> transactionDTOList);
    TransactionDTO getById(Long id);
    TransactionDTO update(TransactionDTO transaction);
    List<TransactionDTO> updateBatch(List<TransactionDTO> transactionDTOList);
    Page<TransactionDTO> list(Pageable page);
    List<TransactionDTO> listByYearMonth(Integer year, Integer month);
    List<TransactionDTO> getByDate(LocalDate date);
    Page<TransactionDTO> getByNote(String note, Pageable page);
    void delete(Long id);
    void deleteBatch(List<TransactionDTO> transactionDTOList);
}
