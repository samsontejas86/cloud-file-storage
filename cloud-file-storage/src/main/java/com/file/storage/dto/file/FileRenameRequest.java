package com.file.storage.dto.file;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileRenameRequest extends FileRequest {

    @NotBlank(message = "Can't get the current name of the file to rename")
    private String currentName;

    @NotBlank(message = "New name of the file cannot be empty")
    private String newName;

    @NotBlank(message = "Can't get the path of the file to rename")
    private String path;

    public FileRenameRequest(String owner, String currentName, String newName, String path) {
        super(owner);
        this.currentName = currentName;
        this.newName = newName;
        this.path = path;
    }
}
