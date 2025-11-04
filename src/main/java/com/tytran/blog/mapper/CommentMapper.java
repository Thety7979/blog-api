package com.tytran.blog.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.tytran.blog.dto.request.CommentRequestDTO;
import com.tytran.blog.dto.response.CommentResponseDTO;
import com.tytran.blog.entity.Comments;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "post", ignore = true)
    @Mapping(target = "user", ignore = true)
    Comments toComment(CommentRequestDTO request);

    @Mapping(source = "comment.user.fullname", target = "userName")
    @Mapping(source = "comment.post.name", target = "postName")
    CommentResponseDTO toDTO(Comments comment);

    @Mapping(source = "comment.user.fullname", target = "userName")
    @Mapping(target = "postName", ignore = true)
    @Named("toDTOWithoutPostName")
    CommentResponseDTO toDTOWithoutPostName(Comments comment);
}
