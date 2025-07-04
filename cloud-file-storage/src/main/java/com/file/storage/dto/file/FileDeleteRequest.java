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
public class FileDeleteRequest extends FileRequest {

    @NotBlank(message = "Can't get the path of the file to delete")
    private String path;

    public FileDeleteRequest(String owner, String path) {
        super(owner);
        this.path = path;
    }
}
