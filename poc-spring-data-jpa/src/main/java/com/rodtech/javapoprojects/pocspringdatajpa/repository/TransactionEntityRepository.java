package com.rodtech.javapoprojects.pocspringdatajpa.repository;

import com.rodtech.javapoprojects.pocspringdatajpa.model.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionEntityRepository extends JpaRepository<TransactionEntity, Long> {

}
