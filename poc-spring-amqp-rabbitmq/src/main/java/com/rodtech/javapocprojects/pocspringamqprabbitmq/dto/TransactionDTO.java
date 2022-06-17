package com.rodtech.javapocprojects.pocspringamqprabbitmq.dto;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDTO {

    private Long id;

    private LocalDateTime created;

    private LocalDateTime edit;

    private LocalDate date;

    private BigDecimal income;

    private BigDecimal expense;

    private BigDecimal balanceValue;

    private String note;

}
