package com.otchi.domaine.users.exceptions;


public class AccountAlreadyExistsException extends Exception {
    public AccountAlreadyExistsException(String email) {
        super("e-mail address already in use :"+email);
    }
}
