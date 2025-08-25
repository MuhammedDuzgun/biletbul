package com.staj.biletbul.exception;

public class VenueAlreadyExistsException extends RuntimeException {
    public VenueAlreadyExistsException(String message) {
        super(message);
    }
}
