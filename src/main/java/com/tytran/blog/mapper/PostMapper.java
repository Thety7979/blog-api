package com.tytran.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.tytran.blog.dto.request.PostRequestDTO;
import com.tytran.blog.dto.response.PostResponseDTO;
import com.tytran.blog.entity.Posts;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    Posts toPost(PostRequestDTO requestDTO);

    @Mapping(source = "post.user.fullname", target = "authorName")
    PostResponseDTO toDTO(Posts post);
}
