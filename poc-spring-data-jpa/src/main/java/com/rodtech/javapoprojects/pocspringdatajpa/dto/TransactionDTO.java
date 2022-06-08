package com.rodtech.javapoprojects.pocspringdatajpa.dto;

import com.rodtech.javapoprojects.pocspringdatajpa.model.TransactionEntity;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
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

    @NotNull
    private LocalDate date;

    @NotNull
    @PositiveOrZero
    private BigDecimal income;

    @NotNull
    @PositiveOrZero
    private BigDecimal expense;

    @NotNull
    private BigDecimal balanceValue;

    private String note;

    public TransactionEntity mapToEntity(){
        return TransactionEntity.builder()
                .id(id)
                .date(date)
                .income(income)
                .expense(expense)
                .balanceValue(balanceValue)
                .note(note)
                .build();
    }

    public static TransactionDTO buildFromEntity(TransactionEntity entity){
        return TransactionDTO.builder()
                .id(entity.getId())
                .created(entity.getCreated())
                .edit(entity.getEdit())
                .date(entity.getDate())
                .income(entity.getIncome())
                .expense(entity.getExpense())
                .balanceValue(entity.getBalanceValue())
                .note(entity.getNote())
                .build();
    }

}
