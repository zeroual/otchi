package com.otchi.domain.social.exceptions;

public class ResourceNotAuthorizedException extends RuntimeException {

    public ResourceNotAuthorizedException(String msg) {
        super(msg);
    }
}