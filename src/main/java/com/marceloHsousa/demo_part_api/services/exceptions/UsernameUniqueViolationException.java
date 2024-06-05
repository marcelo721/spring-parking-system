package com.marceloHsousa.demo_part_api.services.exceptions;

public class UsernameUniqueViolationException extends RuntimeException{

    public UsernameUniqueViolationException(String message) {
        super(message);
    }
}
