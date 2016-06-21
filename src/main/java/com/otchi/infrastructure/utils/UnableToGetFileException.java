package com.otchi.infrastructure.utils;

public class UnableToGetFileException extends RuntimeException {
    public UnableToGetFileException(String message, Throwable throwable) {
        super(message, throwable);
    }

}
