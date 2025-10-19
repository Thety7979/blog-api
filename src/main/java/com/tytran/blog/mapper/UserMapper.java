package com.tytran.blog.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tytran.blog.dto.response.UserDTO;
import com.tytran.blog.entity.Users;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "user.role.name", target = "roleName")
    UserDTO userToUserDTO(Users user);

    List<UserDTO> listUserToUserDTO(List<Users> user);
}