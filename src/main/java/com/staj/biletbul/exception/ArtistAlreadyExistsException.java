package com.staj.biletbul.exception;

public class ArtistAlreadyExistsException extends RuntimeException{
    public ArtistAlreadyExistsException(String message){
        super(message);
    }
}
