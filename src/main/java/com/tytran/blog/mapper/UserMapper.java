package com.tytran.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tytran.blog.dto.request.RegisterRequestDTO;
import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.entity.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "updated_at", ignore = true)
    @Mapping(target = "created_at", ignore = true)
    @Mapping(target = "comments", ignore = true)
    @Mapping(target = "password", ignore = true)
    Users toUser(RegisterRequestDTO requestDTO);

    UserDTO userToUserDTO(Users user);
}