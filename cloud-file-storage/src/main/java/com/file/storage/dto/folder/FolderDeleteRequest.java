package com.file.storage.dto.folder;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FolderDeleteRequest extends FolderRequest {

    @NotBlank(message = "Can't get the path of the folder to delete")
    private String path;

    public FolderDeleteRequest(String owner, String path) {
        super(owner);
        this.path = path;
    }
}
