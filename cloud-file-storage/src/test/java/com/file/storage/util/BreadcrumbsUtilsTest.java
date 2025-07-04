package com.file.storage.util;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertLinesMatch;

class BreadcrumbsUtilsTest {

    @Test
    void getBreadcrumbsForPath() {
        String path = "path/to/obj/";
        List<String> expected = List.of("path", "path/to", "path/to/obj");

        List<String> actual = BreadcrumbsUtils.getBreadcrumbLinksForPath(path);

        assertLinesMatch(expected, actual);
    }

    @Test
    void getBreadcrumbsForPath_empty() {
        String path = "";
        List<String> expected = List.of("");

        List<String> actual = BreadcrumbsUtils.getBreadcrumbLinksForPath(path);

        assertLinesMatch(expected, actual);
    }

    @Test
    void getFolderNamesForPath() {
        String path = "path/to/obj/";
        List<String> expected = List.of("path", "to", "obj");

        List<String> actual = BreadcrumbsUtils.getFolderNamesForPath(path);

        assertLinesMatch(expected, actual);
    }

    @Test
    void getFolderNamesForPath_empty() {
        String path = "";
        List<String> expected = List.of("");

        List<String> actual = BreadcrumbsUtils.getFolderNamesForPath(path);

        assertLinesMatch(expected, actual);
    }
}