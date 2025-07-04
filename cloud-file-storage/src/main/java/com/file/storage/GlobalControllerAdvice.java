package com.file.storage;

import com.file.storage.exception.InvalidUserRegistrationRequestException;
import com.file.storage.exception.file.FileOperationException;
import com.file.storage.exception.file.FileRequestException;
import com.file.storage.exception.folder.FolderOperationException;
import com.file.storage.exception.folder.FolderRequestException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(InvalidUserRegistrationRequestException.class)
    public RedirectView handleInvalidUserRegistrationRequest(InvalidUserRegistrationRequestException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/registration", true);
    }

    @ExceptionHandler(FileRequestException.class)
    public RedirectView handleInvalidFileRequests(FileRequestException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/", true);
    }

    @ExceptionHandler(FolderRequestException.class)
    public RedirectView handleInvalidFileRequests(FolderRequestException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/", true);
    }

    @ExceptionHandler(FileOperationException.class)
    public RedirectView handleInvalidFileRequests(FileOperationException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/", true);
    }

    @ExceptionHandler(FolderOperationException.class)
    public RedirectView handleInvalidFileRequests(FolderOperationException e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/", true);
    }

    @ExceptionHandler(Exception.class)
    public RedirectView handleAll(Exception e, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("error", e.getMessage());

        return new RedirectView("/", true);
    }
}
