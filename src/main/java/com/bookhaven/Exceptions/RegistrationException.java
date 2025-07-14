package com.bookhaven.Exceptions;

public class RegistrationException extends Exception{
    private String errorMessage;

    public RegistrationException(String message){
        this.errorMessage = message;
    }

    @Override
    public String getMessage(){
        return errorMessage;
    }
}
