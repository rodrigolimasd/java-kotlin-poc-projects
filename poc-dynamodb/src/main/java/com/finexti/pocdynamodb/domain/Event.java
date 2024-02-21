package com.finexti.pocdynamodb.domain;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.finexti.pocdynamodb.util.LocalDateConverter;
import com.finexti.pocdynamodb.util.LocalDateTimeConverter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@DynamoDBTable(tableName = "event")
public class Event {
    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAttribute(attributeName = "date_event_id")
    private String dateEventId; // example '2021-05-21#GUID'

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

    public Event() {
    }

    public Event(String dateEventId, LocalDateTime createDate, LocalDate date, double balance, double expense, double income, String note) {
        this.dateEventId = dateEventId;
        this.createDate = createDate;
        this.date = date;
        this.balance = balance;
        this.expense = expense;
        this.income = income;
        this.note = note;
    }

    public String getDateEventId() {
        return dateEventId;
    }

    public void setDateEventId(String dateEventId) {
        this.dateEventId = dateEventId;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getExpense() {
        return expense;
    }

    public void setExpense(double expense) {
        this.expense = expense;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
