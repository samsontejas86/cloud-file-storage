package com.file.storage.exception.file;

public class InvalidFileDeleteRequestException extends FileRequestException {
    
    public InvalidFileDeleteRequestException(String message) {
        super(message);
    }

    public InvalidFileDeleteRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
