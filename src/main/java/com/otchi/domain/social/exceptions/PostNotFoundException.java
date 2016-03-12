package com.otchi.domain.social.exceptions;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long postId) {
        super("POST WITH ID " + postId + " NOT FOUND");
    }
}