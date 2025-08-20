package com.staj.biletbul.exception;

public class EventAlreadyExistsException extends RuntimeException{
    public EventAlreadyExistsException(String message){
        super(message);
    }
}
