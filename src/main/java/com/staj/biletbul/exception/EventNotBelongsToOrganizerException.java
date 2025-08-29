package com.staj.biletbul.exception;

public class EventNotBelongsToOrganizerException extends RuntimeException{
    public EventNotBelongsToOrganizerException(String message) {
        super(message);
    }
}
