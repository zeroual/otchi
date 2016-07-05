package com.otchi.api.facades.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(Long resourceId) {
        super("Resource with id " + resourceId + "is not found");
    }
}
