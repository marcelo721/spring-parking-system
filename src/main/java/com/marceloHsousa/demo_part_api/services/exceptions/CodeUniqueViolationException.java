package com.marceloHsousa.demo_part_api.services.exceptions;

public class CodeUniqueViolationException extends RuntimeException{

    public CodeUniqueViolationException(String message){
        super(message);
    }
}
