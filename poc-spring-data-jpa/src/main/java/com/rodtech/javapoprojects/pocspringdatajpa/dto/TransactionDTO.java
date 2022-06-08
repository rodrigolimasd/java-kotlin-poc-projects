package com.rodtech.javapoprojects.pocspringdatajpa.dto;

import com.rodtech.javapoprojects.pocspringdatajpa.model.TransactionEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private Long id;

    private LocalDateTime created;

    private LocalDateTime edit;

    private LocalDate date;

    private BigDecimal income;

    private BigDecimal expense;

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
