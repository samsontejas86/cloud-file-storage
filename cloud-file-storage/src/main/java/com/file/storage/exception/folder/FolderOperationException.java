package com.file.storage.exception.folder;

public class FolderOperationException extends RuntimeException {

    public FolderOperationException(String message) {
        super(message);
    }

    public FolderOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
