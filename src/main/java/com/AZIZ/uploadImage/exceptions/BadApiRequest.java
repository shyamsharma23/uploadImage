package com.AZIZ.uploadImage.exceptions;

public class BadApiRequest extends RuntimeException{
    public BadApiRequest(String message){
        super(message);
    }
    public BadApiRequest(){
        super("Bad API Request");
    }
}
