package com.staj.biletbul.exception;

public class SeatNotBelongsToTicketTypeException extends RuntimeException{
    public SeatNotBelongsToTicketTypeException(String message) {
        super(message);
    }
}
