package com.staj.biletbul.exception;

public class AlreadyRegisteredEventException extends RuntimeException{
    public AlreadyRegisteredEventException(String message){
        super(message);
    }
}
