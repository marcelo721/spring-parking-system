package com.marceloHsousa.demo_part_api.services.exceptions;

public class ResourcesNotFoundException extends RuntimeException {

    public ResourcesNotFoundException(Object id){
        super(" Resources Not Found. id :" + id);
    }
}