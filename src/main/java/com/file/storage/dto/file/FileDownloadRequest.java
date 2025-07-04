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
public class FileDownloadRequest extends FileRequest {

    @NotBlank(message = "Can't get the file path to download")
    private String path;

    @NotBlank(message = "Can't get the file name to download")
    private String name;

    public FileDownloadRequest(String owner, String path, String name) {
        super(owner);
        this.path = path;
        this.name = name;
    }
}
