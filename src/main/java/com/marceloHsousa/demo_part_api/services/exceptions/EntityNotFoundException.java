package com.marceloHsousa.demo_part_api.services.exceptions;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Object id){
        super(" User  Not Found. id :" + id);
    }
}