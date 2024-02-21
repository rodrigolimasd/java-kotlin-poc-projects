package com.finexti.pocdynamodb.dto;

import java.time.LocalDate;

public record EventDto(
        String id,
        LocalDate date,
        double expense,
        double income,
        String note
) {
}