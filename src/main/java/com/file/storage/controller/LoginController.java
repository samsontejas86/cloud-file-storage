package com.file.storage.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

import static org.springframework.web.servlet.support.RequestContextUtils.getInputFlashMap;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String showLoginPage(Model model, HttpServletRequest request) {
        Map<String, ?> flashMap = getInputFlashMap(request);

        if (flashMap != null && !flashMap.isEmpty()) {
            model.addAttribute("success", flashMap.get("success"));
        }

        return "login";
    }
}
