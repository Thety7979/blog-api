package com.tytran.blog.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.services.AuthService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@TestPropertySource("classpath:test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

    private RegisterRequestDTO request;

    private UserDTO response;

    @BeforeEach
    void initData() {

        LocalDate birthday = LocalDate.of(2005, 11, 22);

        request = RegisterRequestDTO.builder()
                .email("thety@gmail.com")
                .password("12345678")
                .fullname("Trần Thế Ty")
                .birthday(birthday)
                .build();

        response = UserDTO.builder()
                .id(UUID.randomUUID())
                .email("thety@gmail.com")
                .fullname("Trần Thế Ty")
                .birthday(birthday)
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {

        String content = objectMapper.writeValueAsString(request);

        Mockito.when(authService.Register(ArgumentMatchers.any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));
    }

    @Test
    void createUser_emailInvalid_failure() throws Exception {

        request.setEmail("thetygmail.com");
        String content = objectMapper.writeValueAsString(request);

        // Mockito.when(authService.Register(ArgumentMatchers.any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1005))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Invalid email format"));
    }

    @Test
    void createUser_passwordInvalid_failure() throws Exception {

        request.setPassword("123456");
        ;
        String content = objectMapper.writeValueAsString(request);

        // Mockito.when(authService.Register(ArgumentMatchers.any())).thenReturn(response);

        mockMvc.perform(MockMvcRequestBuilders
                .post("/auth/register")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1006))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("Password must be at least 7 character"));
    }
}
