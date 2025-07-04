package com.file.storage.controller;

import com.file.storage.dto.file.FileDeleteRequest;
import com.file.storage.dto.file.FileDownloadRequest;
import com.file.storage.dto.file.FileRenameRequest;
import com.file.storage.dto.file.FileUploadRequest;
import com.file.storage.exception.file.InvalidFileDeleteRequestException;
import com.file.storage.exception.file.InvalidFileDownloadRequestException;
import com.file.storage.exception.file.InvalidFileRenameRequestException;
import com.file.storage.exception.file.InvalidFileUploadRequestException;
import com.file.storage.service.FileService;
import com.file.storage.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping(produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<ByteArrayResource> downloadFile(@Valid @ModelAttribute("fileDownloadRequest") FileDownloadRequest fileDownloadRequest,
                                                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new InvalidFileDownloadRequestException(ValidationUtils.getErrorMessage(bindingResult));
        }

        ByteArrayResource file = fileService.downloadFile(fileDownloadRequest);
        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=" + fileDownloadRequest.getName())
                .body(file);
    }

    @PostMapping
    public RedirectView uploadFile(@Valid @ModelAttribute("fileUploadRequest") FileUploadRequest fileUploadRequest,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            throw new InvalidFileUploadRequestException(ValidationUtils.getErrorMessage(bindingResult));
        }

        fileService.uploadFile(fileUploadRequest);

        redirectAttributes.addFlashAttribute("success", "File uploaded successfully");
        return new RedirectView("/");
    }

    @PutMapping
    public RedirectView renameFile(@Valid @ModelAttribute("fileRenameRequest") FileRenameRequest fileRenameRequest,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            throw new InvalidFileRenameRequestException(ValidationUtils.getErrorMessage(bindingResult));
        }

        fileService.renameFile(fileRenameRequest);

        redirectAttributes.addFlashAttribute("success", "File renamed successfully");
        return new RedirectView("/");
    }

    @DeleteMapping
    public RedirectView deleteFile(@Valid @ModelAttribute("fileDeleteRequest") FileDeleteRequest fileDeleteRequest,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            throw new InvalidFileDeleteRequestException(ValidationUtils.getErrorMessage(bindingResult));
        }

        fileService.deleteFile(fileDeleteRequest);

        redirectAttributes.addFlashAttribute("success", "File deleted successfully");
        return new RedirectView("/");
    }
}
