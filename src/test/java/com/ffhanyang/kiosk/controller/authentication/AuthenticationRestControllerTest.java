package com.ffhanyang.kiosk.controller.authentication;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ffhanyang.kiosk.security.AuthenticationRequest;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;

import java.util.logging.Logger;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationRestControllerTest {

    private Logger log = Logger.getLogger(this.getClass().getName());

    @Autowired
    private MockMvc mockMvc;


    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("없는 유저로 로그인했을 때 Unauthorized 반환")
    void loginUnAuthorizedTest() throws Exception {
        // given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("invalid@gmail.com", "invalid");

        // when & then
        mockMvc.perform(
                post("/api/auth/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authenticationRequest))
            )
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.success", is(false)))
            .andExpect(jsonPath("$.error.message", is("Authentication error (cause: Could not found 'Member' with query values (Email[member_email=invalid@gmail.com]))")))
            .andExpect(jsonPath("$.error.status", is(HttpStatus.UNAUTHORIZED.value())));
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인했을 때 Unauthorized 반환")
    void loginUnAuthorizedTest2() throws Exception {
        // given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("tester0@gmail.com", "invalid");

        // when & then
        mockMvc.perform(
                post("/api/auth/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authenticationRequest))
            )
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andExpect(jsonPath("$.success", is(false)))
            .andExpect(jsonPath("$.error.message", is("Authentication error (cause: Invalid credentials.)")))
            .andExpect(jsonPath("$.error.status", is(HttpStatus.UNAUTHORIZED.value())));
    }

    @Test
    @DisplayName("로그인 성공")
    void loginSuccessTest() throws Exception {
        // given
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("tester0@gmail.com", "tester0");

        // when & then
        mockMvc.perform(
                post("/api/auth/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(authenticationRequest))
            )
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.success", is(true)))
            .andExpect(jsonPath("$.response.apiToken").exists());
    }
}