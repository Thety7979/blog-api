package com.tytran.blog.services.implement;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tytran.blog.dto.request.CommentRequestDTO;
import com.tytran.blog.dto.response.CommentResponseDTO;
import com.tytran.blog.entity.Comments;
import com.tytran.blog.entity.Posts;
import com.tytran.blog.entity.Users;
import com.tytran.blog.exception.AppException;
import com.tytran.blog.exception.ErrorCode;
import com.tytran.blog.mapper.CommentMapper;
import com.tytran.blog.repository.CommentRepository;
import com.tytran.blog.repository.PostRepository;
import com.tytran.blog.repository.UserRepository;
import com.tytran.blog.services.CommentService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentServiceImpl implements CommentService {

    CommentRepository commentRepository;

    CommentMapper commentMapper;

    PostRepository postRepository;

    UserRepository userRepository;

    @Override
    public CommentResponseDTO create(CommentRequestDTO requestDTO) {
        var context = SecurityContextHolder.getContext().getAuthentication();
        Posts post = postRepository
                .findByName(requestDTO.getPostName())
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        Users user = userRepository
                .findByEmail(context.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Comments comment = commentMapper.toComment(requestDTO);
        comment.setPost(post);
        comment.setUser(user);
        commentRepository.save(comment);
        return commentMapper.toDTO(comment);
    }

    @Override
    public List<CommentResponseDTO> getAllComment() {
        return commentRepository.findAll().stream().map(commentMapper::toDTO).toList();
    }

    @Override
    public Boolean delete(UUID id) {
        var context = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository
                .findByEmail(context.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Comments comments =
                commentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND));
        if (!Objects.equals(user.getId(), comments.getUser().getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        commentRepository.delete(comments);
        return true;
    }
}
