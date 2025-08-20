package com.staj.biletbul.exception;

public class ArtistNotFoundException extends RuntimeException{
    public ArtistNotFoundException(String message){
        super(message);
    }
}
