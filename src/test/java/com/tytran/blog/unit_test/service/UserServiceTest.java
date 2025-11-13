package com.tytran.blog.unit_test.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.entity.Users;
import com.tytran.blog.exception.AppException;
import com.tytran.blog.repository.UserRepository;
import com.tytran.blog.services.UserService;

@SpringBootTest
@Transactional
@TestPropertySource("classpath:test.properties")
@AutoConfigureMockMvc
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    private RegisterRequestDTO request;

    private Users user;

    private Users userOther;

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

        userOther = Users.builder()
                .id(UUID.fromString("7001a230-d877-4ddf-a0b0-917f262f765d"))
                .email("thety2005@gmail.com")
                .fullname("Trần Thế Tý")
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

    @Test
    @WithMockUser(username = "thety@gmail.com")
    void me_valid_success() {
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        var response = userService.me();

        Assertions.assertThat(response.getEmail()).isEqualTo("thety@gmail.com");
        Assertions.assertThat(response.getFullname()).isEqualTo("Trần Thế Ty");
    }

    @Test
    @WithMockUser(username = "thety@gmail.com")
    void me_invalid_failure() {
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        var response = assertThrows(AppException.class, () -> userService.me());

        Assertions.assertThat(response.getErrorCode().getCode()).isEqualTo(1004);
        Assertions.assertThat(response.getErrorCode().getMessage()).isEqualTo("User not found");
    }

    @Test
    @WithMockUser("thety@gmail.com")
    void getUserById_valid_success() {
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        var response = userService.getUserById(user.getId());

        Assertions.assertThat(response.getEmail()).isEqualTo("thety@gmail.com");
        Assertions.assertThat(response.getFullname()).isEqualTo("Trần Thế Ty");
    }

    @Test
    @WithMockUser("thety@gmail.com")
    void getUserById_invalidFindByEmail_failure() {
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        var response = assertThrows(AppException.class, () -> userService.getUserById(user.getId()));

        Assertions.assertThat(response.getErrorCode().getCode()).isEqualTo(1004);
        Assertions.assertThat(response.getErrorCode().getMessage()).isEqualTo("User not found");
    }

    @Test
    @WithMockUser("thety@gmail.com")
    void getUserById_invalidFindById_failure() {
        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.empty());

        var response = assertThrows(AppException.class, () -> userService.getUserById(user.getId()));

        Assertions.assertThat(response.getErrorCode().getCode()).isEqualTo(1004);
        Assertions.assertThat(response.getErrorCode().getMessage()).isEqualTo("User not found");
    }

    @Test
    @WithMockUser("thety@gmail.com")
    void getUserById_unauthorized_failure() {

        Mockito.when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.findById(any())).thenReturn(Optional.of(userOther));

        var response = assertThrows(AppException.class, () -> userService.getUserById(userOther.getId()));

        Assertions.assertThat(response.getErrorCode().getCode()).isEqualTo(1008);
        Assertions.assertThat(response.getErrorCode().getMessage()).isEqualTo("Permission denied");
    }
}
