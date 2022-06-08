package com.rodtech.javapoprojects.pocspringdatajpa.repository;

import com.rodtech.javapoprojects.pocspringdatajpa.dto.TransactionFilterDTO;
import com.rodtech.javapoprojects.pocspringdatajpa.model.TransactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionEntityRepositoryQueryCriteria {
    Page<TransactionEntity> filter(TransactionFilterDTO filter, Pageable page);
}
