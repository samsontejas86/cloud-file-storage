package com.file.storage.dto.folder;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class FolderRequest {

    @NotBlank(message = "Can't get the owner of the file")
    private String owner;
}
