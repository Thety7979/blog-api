package com.tytran.blog.integration_test.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.response.UserDTO;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerIntegrationTest {

    @Container
    static final PostgreSQLContainer PostgreSQLContainer = new PostgreSQLContainer("postgres:17.4");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private RegisterRequestDTO request;

    private UserDTO response;

    @BeforeEach
    void initData() {

        LocalDate birthday = LocalDate.of(2005, 11, 22);

        request = RegisterRequestDTO.builder()
                .email("thety126@gmail.com")
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

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("result.email").value("thety126@gmail.com"));
    }
}
