package com.tytran.blog.services;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;

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

@Transactional
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InitService {

    PasswordEncoder passwordEncoder;

    UserRepository userRepository;

    RoleRepository roleRepository;

    public void createUserAdmin() {
        if (userRepository.findByEmail("admin@gmail.com").isEmpty()) {
            List<Role> role = roleRepository.findAllByNameIn(List.of("ADMIN"));
            Users user = Users.builder()
                    .email("admin@gmail.com")
                    .password(passwordEncoder.encode("admin"))
                    .fullname("Admin")
                    .created_at(LocalDateTime.now())
                    .updated_at(LocalDateTime.now())
                    .roles(new HashSet<>(role))
                    .build();
            userRepository.save(user);
        }
    }
}
