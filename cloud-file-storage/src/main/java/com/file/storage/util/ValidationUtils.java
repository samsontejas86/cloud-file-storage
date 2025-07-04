package com.file.storage.util;

import org.springframework.validation.BindingResult;

public class ValidationUtils {

    public static String getErrorMessage(BindingResult bindingResult) {
        return bindingResult.getAllErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Something went wrong");
    }
}
