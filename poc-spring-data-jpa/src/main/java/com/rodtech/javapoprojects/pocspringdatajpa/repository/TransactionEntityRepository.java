package com.rodtech.javapoprojects.pocspringdatajpa.repository;

import com.rodtech.javapoprojects.pocspringdatajpa.model.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Long> {

    Page<TransactionEntity> findAllByOrderByDate(Pageable pageable);
    Page<TransactionEntity> findByDateBetweenOrderByDate(LocalDate start, LocalDate end, Pageable pageable);

}
