package com.file.storage.exception.file;

public class InvalidFileRenameRequestException extends FileRequestException {

    public InvalidFileRenameRequestException(String message) {
        super(message);
    }

    public InvalidFileRenameRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
