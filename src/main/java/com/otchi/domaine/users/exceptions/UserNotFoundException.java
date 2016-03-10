package com.otchi.domaine.users.exceptions;


public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String userId) {
        super("USER WITH ID " + userId + " NOT FOUND");
    }
}
