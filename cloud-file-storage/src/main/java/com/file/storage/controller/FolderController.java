package com.file.storage.controller;

import com.file.storage.dto.folder.FolderDeleteRequest;
import com.file.storage.dto.folder.FolderRenameRequest;
import com.file.storage.dto.folder.FolderUploadRequest;
import com.file.storage.exception.folder.InvalidFolderDeleteRequestException;
import com.file.storage.exception.folder.InvalidFolderRenameRequestException;
import com.file.storage.exception.folder.InvalidFolderUploadRequestException;
import com.file.storage.service.FolderService;
import com.file.storage.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public RedirectView uploadFolder(@Valid @ModelAttribute("folderUploadRequest") FolderUploadRequest folderUploadRequest,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (isFolderEmpty(folderUploadRequest)) {
            throw new InvalidFolderUploadRequestException("Empty folder upload is not supported");
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidFolderUploadRequestException(ValidationUtils.getErrorMessage(bindingResult));
        }

        folderService.uploadFolder(folderUploadRequest);

        redirectAttributes.addFlashAttribute("success", "Folder uploaded successfully");
        return new RedirectView("/");
    }

    @PutMapping
    public RedirectView renameFolder(@Valid @ModelAttribute("folderRenameRequest") FolderRenameRequest folderRenameRequest,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            throw new InvalidFolderRenameRequestException(ValidationUtils.getErrorMessage(bindingResult));
        }

        folderService.renameFolder(folderRenameRequest);

        redirectAttributes.addFlashAttribute("success", "FOlder renamed successfully");
        return new RedirectView("/");
    }

    @DeleteMapping
    public RedirectView deleteFolder(@Valid @ModelAttribute("folderDeleteRequest") FolderDeleteRequest folderDeleteRequest,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            throw new InvalidFolderDeleteRequestException(ValidationUtils.getErrorMessage(bindingResult));
        }

        folderService.deleteFolder(folderDeleteRequest);

        redirectAttributes.addFlashAttribute("success", "Folder deleted successfully");
        return new RedirectView("/");
    }

    private static boolean isFolderEmpty(FolderUploadRequest request) {
        if (request.getFiles().size() == 1) {
            String filename = request.getFiles().get(0).getOriginalFilename();

            return filename == null || filename.isBlank();
        }
        return false;
    }
}
