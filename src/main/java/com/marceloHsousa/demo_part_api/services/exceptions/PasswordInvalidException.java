package com.marceloHsousa.demo_part_api.services.exceptions;

public class PasswordInvalidException extends RuntimeException{


    public PasswordInvalidException(String message) {
        super(message);
    }
}
