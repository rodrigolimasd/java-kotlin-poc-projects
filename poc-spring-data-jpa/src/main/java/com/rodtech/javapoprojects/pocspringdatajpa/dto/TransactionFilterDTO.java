package com.rodtech.javapoprojects.pocspringdatajpa.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TransactionFilterDTO {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private BigDecimal income;
    private BigDecimal incomeFrom;
    private BigDecimal incomeUpTo;

    private BigDecimal expense;
    private BigDecimal expenseFrom;
    private BigDecimal expenseUpTo;

    private BigDecimal balanceValue;
    private BigDecimal balanceValueFrom;
    private BigDecimal balanceValueUpTo;

    private String note;
    private String noteContains;

}
