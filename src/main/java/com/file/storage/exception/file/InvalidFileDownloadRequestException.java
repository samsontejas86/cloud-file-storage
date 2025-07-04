package com.file.storage.exception.file;

public class InvalidFileDownloadRequestException extends FileRequestException {

    public InvalidFileDownloadRequestException(String message) {
        super(message);
    }

    public InvalidFileDownloadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
