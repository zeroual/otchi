package com.otchi.infrastructure.storage;

/**
 * Created by MJR2 on 6/12/2016.
 */
public class BlobObjectPutException extends RuntimeException {
    public BlobObjectPutException(String message, Throwable cause) {
        super(message, cause);
    }
}
