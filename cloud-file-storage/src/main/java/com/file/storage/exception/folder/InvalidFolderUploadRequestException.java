package com.file.storage.exception.folder;

public class InvalidFolderUploadRequestException extends FolderRequestException {

    public InvalidFolderUploadRequestException(String message) {
        super(message);
    }

    public InvalidFolderUploadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
