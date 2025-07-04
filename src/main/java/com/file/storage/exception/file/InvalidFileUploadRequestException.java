package com.file.storage.exception.file;

public class InvalidFileUploadRequestException extends FileRequestException {

    public InvalidFileUploadRequestException(String message) {
        super(message);
    }

    public InvalidFileUploadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
