package com.rodtech.javapoprojects.pocspringdatajpa.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(String message) {
        super(message);
    }
}
