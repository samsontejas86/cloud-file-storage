package com.file.storage.exception.file;

public abstract class FileRequestException extends RuntimeException {

    public FileRequestException(String message) {
        super(message);
    }

    public FileRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
