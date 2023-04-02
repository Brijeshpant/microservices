package com.brij.exceptions;


public class MissingRecordException extends RuntimeException {
    public MissingRecordException(String message) {
        super(message);
    }
}
