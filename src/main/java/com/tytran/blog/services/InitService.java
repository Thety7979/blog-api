package com.tytran.blog.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tytran.blog.entity.Role;
import com.tytran.blog.entity.Users;
import com.tytran.blog.repository.RoleRepository;
import com.tytran.blog.repository.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Transactional
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InitService {

    PasswordEncoder passwordEncoder;

    UserRepository userRepository;

    RoleRepository roleRepository;

    @NonFinal
    @Value("${admin.password}")
    protected String ADMIN_PASSWORD;

    @NonFinal
    @Value("${admin.email}")
    protected String ADMIN_EMAIL;

    public void createUserAdmin() {
        if (userRepository.findByEmail(ADMIN_EMAIL).isEmpty()) {
            List<Role> role = roleRepository.findAllByNameIn(List.of(com.tytran.blog.enums.Role.ADMIN.name()));
            Users user = Users.builder()
                    .email(ADMIN_EMAIL)
                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                    .fullname("Admin")
                    .birthday(LocalDate.of(2005, 11, 22))
                    .created_at(LocalDateTime.now())
                    .updated_at(LocalDateTime.now())
                    .roles(new HashSet<>(role))
                    .build();
            userRepository.save(user);
        }
    }
}
