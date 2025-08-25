package com.staj.biletbul.exception;

public class EventTimeConflictException extends RuntimeException{
    public EventTimeConflictException(String message) {
        super(message);
    }
}
