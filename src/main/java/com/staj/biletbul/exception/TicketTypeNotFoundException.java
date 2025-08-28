package com.staj.biletbul.exception;

public class TicketTypeNotFoundException extends RuntimeException {
    public TicketTypeNotFoundException(String message) {
        super(message);
    }
}
