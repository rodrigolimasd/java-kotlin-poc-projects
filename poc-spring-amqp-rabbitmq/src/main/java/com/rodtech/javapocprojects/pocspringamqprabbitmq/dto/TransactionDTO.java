package com.rodtech.javapocprojects.pocspringamqprabbitmq.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private BigDecimal income;
    private BigDecimal expense;
    private BigDecimal balanceValue;
    private String note;
    private String type;

}
