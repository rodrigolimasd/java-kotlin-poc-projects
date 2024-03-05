package com.finexti.pocdynamodb.dto;

import java.time.LocalDate;

public record EventCreatDto(
        String id,
        LocalDate date,
        double expense,
        double income,
        String note
) {
}