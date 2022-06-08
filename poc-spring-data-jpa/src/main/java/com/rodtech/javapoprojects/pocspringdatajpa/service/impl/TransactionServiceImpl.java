package com.rodtech.javapoprojects.pocspringdatajpa.service.impl;

import com.rodtech.javapoprojects.pocspringdatajpa.dto.TransactionDTO;
import com.rodtech.javapoprojects.pocspringdatajpa.model.TransactionEntity;
import com.rodtech.javapoprojects.pocspringdatajpa.repository.TransactionEntityRepository;
import com.rodtech.javapoprojects.pocspringdatajpa.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<TransactionDTO> createBatch(List<TransactionDTO> transactionDTOList) {
        List<TransactionEntity> entities = transactionDTOList.stream().map(TransactionDTO::mapToEntity)
                .collect(Collectors.toList());
        entities.forEach(p-> {
            p.setCreated(LocalDateTime.now());
            p.setId(null);
        });
        entities = transactionEntityRepository.saveAll(entities);

        return entities.stream().map(TransactionDTO::buildFromEntity).collect(Collectors.toList());
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
    public List<TransactionDTO> updateBatch(List<TransactionDTO> transactionDTOList) {
        List<TransactionEntity> entities = transactionEntityRepository.findAllById(transactionDTOList.stream()
                .map(TransactionDTO::getId).collect(Collectors.toList()));

        //match update
       transactionDTOList.stream().filter(p-> entities.stream().anyMatch(e-> e.getId().equals(p.getId())))
               .forEach(transaction-> {
                   entities.stream().filter(entity-> entity.getId().equals(transaction.getId())).findFirst()
                           .ifPresent(entity-> {
                               entity.setEdit(LocalDateTime.now());
                               entity.setDate(transaction.getDate());
                               entity.setIncome(transaction.getIncome());
                               entity.setExpense(transaction.getExpense());
                               entity.setBalanceValue(transaction.getBalanceValue());
                               entity.setNote(transaction.getNote());
                           });
               });
        transactionEntityRepository.saveAll(entities);
        return entities.stream().map(TransactionDTO::buildFromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<TransactionDTO> list(Pageable page) {
        return transactionEntityRepository.findAllByOrderByDate(page).map(TransactionDTO::buildFromEntity);
    }

    @Override
    public List<TransactionDTO> listByYearMonth(Integer year, Integer month) {

        LocalDate start = LocalDate.now().withDayOfMonth(1).withMonth(month).withYear(year);
        LocalDate end = start.withDayOfMonth(start.getMonth().length(start.isLeapYear()));

        return transactionEntityRepository.findByDateBetweenOrderByDate(start, end)
                .stream().map(TransactionDTO::buildFromEntity).collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getByDate(LocalDate date) {
        return transactionEntityRepository.findAllByDateOrderByDate(date)
                .stream().map(TransactionDTO::buildFromEntity).collect(Collectors.toList());
    }

    @Override
    public Page<TransactionDTO> getByNote(String note, Pageable page) {
        return transactionEntityRepository.findAllByNoteContainsOrderByDate(note, page)
                .map(TransactionDTO::buildFromEntity);
    }

    @Override
    public void delete(Long id) {
        TransactionEntity entity = transactionEntityRepository.findById(id).orElseThrow();
        transactionEntityRepository.delete(entity);
    }
}
