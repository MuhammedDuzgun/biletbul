package com.staj.biletbul.exception;

public class EventNotActiveException extends RuntimeException {
    public EventNotActiveException(String message) {
        super(message);
    }
}
