package com.otchi.api.facades.exceptions;

import com.otchi.domain.social.exceptions.PostNotFoundException;
import com.otchi.domain.social.exceptions.ResourceNotAuthorizedException;
import com.otchi.domain.users.exceptions.AccountAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    //FIXME log exceptions ?
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NoSuchElementException.class, ResourceNotFoundException.class})
    public final void handleNotFound() {
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({IllegalStateException.class})
    public final void handleConflict() {
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity defaultErrorHandler(IllegalArgumentException e) {
        LOGGER.warn("Wrong arguments given to request", e);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity constraintError(ConstraintViolationException e) {
        LOGGER.warn("Wrong arguments given to request", e);
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    public ResponseEntity accountAlreadyExist(AccountAlreadyExistsException e) {
        LOGGER.warn("Account already exists:", e);
        return new ResponseEntity(HttpStatus.CONFLICT);
    }

    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity postNotFound(PostNotFoundException e) {
        LOGGER.warn(e.getMessage());
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceNotAuthorizedException.class)
    public ResponseEntity postNotAuthorized(ResourceNotAuthorizedException e) {
        LOGGER.warn(e.getMessage());
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);
    }
}
