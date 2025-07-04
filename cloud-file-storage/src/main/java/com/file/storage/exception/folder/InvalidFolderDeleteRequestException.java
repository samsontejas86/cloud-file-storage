package com.file.storage.exception.folder;

public class InvalidFolderDeleteRequestException extends FolderRequestException {

    public InvalidFolderDeleteRequestException(String message) {
        super(message);
    }

    public InvalidFolderDeleteRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
