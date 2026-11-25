package com.applypilot.exceptions;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(String message)
    {
        super(message);
    }
}
