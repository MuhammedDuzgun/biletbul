package com.staj.biletbul.exception;

public class EventCategoryAlreadyExistsException extends RuntimeException {
    public EventCategoryAlreadyExistsException(String message) {
        super(message);
    }
}
