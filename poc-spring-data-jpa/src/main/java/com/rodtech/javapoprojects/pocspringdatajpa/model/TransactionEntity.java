package com.rodtech.javapoprojects.pocspringdatajpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime create;

    @Column
    @LastModifiedDate
    private LocalDateTime edit;

    private LocalDate date;

    private BigDecimal income;

    private BigDecimal expense;

    private BigDecimal balanceValue;

    private String note;

}
