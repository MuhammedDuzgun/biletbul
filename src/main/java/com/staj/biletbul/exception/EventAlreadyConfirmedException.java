package com.staj.biletbul.exception;

public class EventAlreadyConfirmedException extends RuntimeException{
    public EventAlreadyConfirmedException(String message) {
        super(message);
    }
}
