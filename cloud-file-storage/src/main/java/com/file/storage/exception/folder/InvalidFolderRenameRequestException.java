package com.file.storage.exception.folder;

public class InvalidFolderRenameRequestException extends FolderRequestException {

    public InvalidFolderRenameRequestException(String message) {
        super(message);
    }

    public InvalidFolderRenameRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
