package com.file.storage.controller;

import com.file.storage.config.TestWebSecurityConfiguration;
import com.file.storage.dto.UserRegistrationRequest;
import com.file.storage.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RegistrationController.class)
@Import(TestWebSecurityConfiguration.class)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @Test
    void register_nullFields_redirect() throws Exception {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(
                null,
                null,
                null
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .content(userRegistrationRequest.toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(
                status().is3xxRedirection()
        );
        verify(userService, never()).register(any());
    }

    @Test
    void register_nullUsername_redirect() throws Exception {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(
                null,
                "qwerty",
                "email@domain.com"
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .content(userRegistrationRequest.toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(
                status().is3xxRedirection()
        );
        verify(userService, never()).register(any());
    }

    @Test
    void register_nullPassword_redirect() throws Exception {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(
                "qwerty",
                null,
                "email@domain.com"
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .content(userRegistrationRequest.toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(
                status().is3xxRedirection()
        );
        verify(userService, never()).register(any());
    }

    @Test
    void register_nullEmail_redirect() throws Exception {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(
                "qwerty",
                "qwerty",
                null
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .content(userRegistrationRequest.toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(
                status().is3xxRedirection()
        );
        verify(userService, never()).register(any());
    }

    @Test
    void register_badEmail_redirect() throws Exception {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(
                "qwerty",
                "qwerty",
                "qwerty"
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .content(userRegistrationRequest.toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(
                status().is3xxRedirection()
        );
        verify(userService, never()).register(any());
    }

    @Test
    void register_shortPassword_redirect() throws Exception {
        UserRegistrationRequest userRegistrationRequest = new UserRegistrationRequest(
                "qwerty",
                "qwe",
                "email@domain.com"
        );

        mvc.perform(MockMvcRequestBuilders
                .post("/registration")
                .content(userRegistrationRequest.toString())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
        ).andExpect(
                status().is3xxRedirection()
        );
        verify(userService, never()).register(any());
    }
}