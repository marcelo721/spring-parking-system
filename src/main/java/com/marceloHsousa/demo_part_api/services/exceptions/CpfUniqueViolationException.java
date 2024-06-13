package com.marceloHsousa.demo_part_api.services.exceptions;

public class CpfUniqueViolationException extends RuntimeException{

    public CpfUniqueViolationException(String message) {
        super(message);
    }
}
