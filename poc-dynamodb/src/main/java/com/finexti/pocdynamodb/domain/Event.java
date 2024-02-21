package com.finexti.pocdynamodb.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import com.finexti.pocdynamodb.util.LocalDateConverter;
import com.finexti.pocdynamodb.util.LocalDateTimeConverter;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "event")
public class Event {

    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBRangeKey(attributeName = "date_event_id")
    private String dateEventId;

    @DynamoDBAttribute(attributeName = "create_date")
    @DynamoDBTypeConverted(converter = LocalDateTimeConverter.class)
    private LocalDateTime createDate;

    @DynamoDBAttribute(attributeName = "date")
    @DynamoDBTypeConverted(converter = LocalDateConverter.class)
    private LocalDate date;

    @DynamoDBAttribute(attributeName = "balance")
    private double balance;

    @DynamoDBAttribute(attributeName = "expense")
    private double expense;

    @DynamoDBAttribute(attributeName = "income")
    private double income;

    @DynamoDBAttribute(attributeName = "note")
    private String note;
}
