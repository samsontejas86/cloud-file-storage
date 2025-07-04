package com.file.storage.controller;

import com.file.storage.dto.MinioObjectDto;
import com.file.storage.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @GetMapping
    public String search(@AuthenticationPrincipal User user, @RequestParam("query") String query, Model model) {
        if (query == null || query.isBlank()) {
            throw new IllegalArgumentException("Search query cannot be empty");
        }

        List<MinioObjectDto> results = searchService.search(user.getUsername(), query);

        model.addAttribute("searchResults", results.isEmpty() ? null : results);

        return "search";
    }
}
