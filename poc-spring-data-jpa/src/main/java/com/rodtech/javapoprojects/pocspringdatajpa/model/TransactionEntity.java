package com.rodtech.javapoprojects.pocspringdatajpa.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "transaction_tb")
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, updatable = false)
    private LocalDateTime created;

    @Column
    private LocalDateTime edit;

    @Column
    private LocalDate date;

    @Column
    private BigDecimal income;

    @Column
    private BigDecimal expense;

    @Column
    private BigDecimal balanceValue;

    @Column
    private String note;

}
