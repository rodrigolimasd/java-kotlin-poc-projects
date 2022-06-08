package com.rodtech.javapoprojects.pocspringdatajpa.service.impl;

import com.rodtech.javapoprojects.pocspringdatajpa.dto.TransactionDTO;
import com.rodtech.javapoprojects.pocspringdatajpa.model.TransactionEntity;
import com.rodtech.javapoprojects.pocspringdatajpa.repository.TransactionEntityRepository;
import com.rodtech.javapoprojects.pocspringdatajpa.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionEntityRepository transactionEntityRepository;

    public TransactionServiceImpl(TransactionEntityRepository transactionEntityRepository) {
        this.transactionEntityRepository = transactionEntityRepository;
    }

    @Override
    public TransactionDTO create(TransactionDTO transaction) {
        TransactionEntity entity = transaction.mapToEntity();
        entity.setCreated(LocalDateTime.now());
        return TransactionDTO.buildFromEntity(transactionEntityRepository.save(entity));
    }

    @Override
    public TransactionDTO getById(Long id) {
        return TransactionDTO.buildFromEntity(transactionEntityRepository.findById(id).orElseThrow());
    }

    @Override
    public TransactionDTO update(TransactionDTO transaction) {
        TransactionEntity entity = transactionEntityRepository.findById(transaction.getId()).orElseThrow();

        entity.setEdit(LocalDateTime.now());
        entity.setDate(transaction.getDate());
        entity.setIncome(transaction.getIncome());
        entity.setExpense(transaction.getExpense());
        entity.setBalanceValue(transaction.getBalanceValue());
        entity.setNote(transaction.getNote());

        transactionEntityRepository.save(entity);

        return TransactionDTO.buildFromEntity(entity);
    }

    @Override
    public Page<TransactionDTO> list(Pageable page) {
        return transactionEntityRepository.findAll(page).map(TransactionDTO::buildFromEntity);
    }

    @Override
    public void delete(Long id) {
        TransactionEntity entity = transactionEntityRepository.findById(id).orElseThrow();
        transactionEntityRepository.delete(entity);
    }
}
