package com.staj.biletbul.exception;

public class VenueNotBelongsToCityException extends RuntimeException {
    public VenueNotBelongsToCityException(String message) {
        super(message);
    }
}
