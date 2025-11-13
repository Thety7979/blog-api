package com.tytran.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.tytran.blog.dto.request.PostRequestDTO;
import com.tytran.blog.dto.response.PostResponseDTO;
import com.tytran.blog.entity.Posts;

@Mapper(
        componentModel = "spring",
        uses = {CommentMapper.class})
public interface PostMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    Posts toPost(PostRequestDTO requestDTO);

    @Mapping(source = "post.user.fullname", target = "authorName")
    @Mapping(target = "comments", qualifiedByName = "toDTOWithoutPostName")
    PostResponseDTO toDTO(Posts post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "comments", ignore = true)
    void updatePost(@MappingTarget Posts post, PostRequestDTO requestDTO);
}
