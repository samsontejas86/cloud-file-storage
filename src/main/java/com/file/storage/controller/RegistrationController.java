package com.file.storage.controller;

import com.file.storage.dto.UserRegistrationRequest;
import com.file.storage.exception.InvalidUserRegistrationRequestException;
import com.file.storage.service.UserService;
import com.file.storage.util.ValidationUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @GetMapping
    public String showRegistrationPage(Model model) {
        model.addAttribute("userRegistrationRequest", new UserRegistrationRequest());

        return "registration";
    }

    @PostMapping
    public RedirectView register(@Valid @ModelAttribute("userRegistrationRequest") UserRegistrationRequest userRegistrationRequest,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            throw new InvalidUserRegistrationRequestException(ValidationUtils.getErrorMessage(bindingResult));
        }

        userService.register(userRegistrationRequest);

        redirectAttributes.addFlashAttribute("success", "Registration is successful, please login");
        return new RedirectView("/login");
    }
}
