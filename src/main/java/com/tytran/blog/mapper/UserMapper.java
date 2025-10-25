package com.tytran.blog.mapper;

import org.mapstruct.Mapper;

import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.entity.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO userToUserDTO(Users user);
}