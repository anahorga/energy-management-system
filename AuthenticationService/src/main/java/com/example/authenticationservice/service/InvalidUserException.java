package com.example.authenticationservice.service;

public class InvalidUserException extends RuntimeException{
    public InvalidUserException(String errs){
        super(errs);
    }
}
