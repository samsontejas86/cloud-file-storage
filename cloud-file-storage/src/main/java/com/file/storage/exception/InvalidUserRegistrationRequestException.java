package com.file.storage.exception;

public class InvalidUserRegistrationRequestException extends RuntimeException {

    public InvalidUserRegistrationRequestException(String message) {
        super(message);
    }

    public InvalidUserRegistrationRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
