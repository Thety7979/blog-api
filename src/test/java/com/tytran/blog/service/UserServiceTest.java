package com.tytran.blog.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.entity.Users;
import com.tytran.blog.exception.AppException;
import com.tytran.blog.repository.UserRepository;
import com.tytran.blog.services.UserService;

@SpringBootTest
@Transactional
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private RegisterRequestDTO request;

    private Users user;

    private LocalDate birthday;

    @BeforeEach
    void initData() {

        birthday = LocalDate.of(2005, 11, 22);

        request = RegisterRequestDTO.builder()
                .email("thety@gmail.com")
                .password("12345678")
                .fullname("Trần Thế Ty")
                .birthday(birthday)
                .build();

        user = Users.builder()
                .id(UUID.fromString("7001a230-d877-4ddf-a0b0-917f262f765c"))
                .email("thety@gmail.com")
                .fullname("Trần Thế Ty")
                .birthday(birthday)
                .build();
    }

    @Test
    void createUser_validRequest_success() throws Exception {

        Mockito.when(userRepository.existsByEmail(anyString())).thenReturn(false);
        Mockito.when(userRepository.save(any())).thenReturn(user);

        var response = userService.saveUser(request);

        Assertions.assertThat(response.getId()).isEqualTo(UUID.fromString("7001a230-d877-4ddf-a0b0-917f262f765c"));
        Assertions.assertThat(response.getEmail()).isEqualTo("thety@gmail.com");
        Assertions.assertThat(response.getFullname()).isEqualTo("Trần Thế Ty");
        Assertions.assertThat(response.getBirthday()).isEqualTo(birthday);
    }

    @Test
    void createUser_userExists_success() throws Exception {

        Mockito.when(userRepository.existsByEmail(anyString())).thenReturn(true);

        var exception = assertThrows(AppException.class, () -> userService.saveUser(request));

        Assertions.assertThat(exception.getErrorCode().getCode()).isEqualTo(1001);
        Assertions.assertThat(exception.getErrorCode().getMessage()).isEqualTo("User existed");
    }

}
