package com.staj.biletbul.exception;

public class CityAlreadyExistsException extends RuntimeException{
    public CityAlreadyExistsException(String message){
        super(message);
    }
}
