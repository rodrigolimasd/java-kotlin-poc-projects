package com.rodtech.javapoprojects.pocspringdatajpa.repository;

import com.rodtech.javapoprojects.pocspringdatajpa.model.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Long> {

    Page<TransactionEntity> findAllByOrderByDate(Pageable pageable);
    List<TransactionEntity> findByDateBetweenOrderByDate(LocalDate start, LocalDate end);
    List<TransactionEntity> findAllByDateOrderByDate(LocalDate date);
    Page<TransactionEntity> findAllByNoteContainsOrderByDate(String note, Pageable pageable);

}
