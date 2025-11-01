package com.tytran.blog.services.implement;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tytran.blog.dto.request.PostRequestDTO;
import com.tytran.blog.dto.response.PostResponseDTO;
import com.tytran.blog.entity.Posts;
import com.tytran.blog.entity.Users;
import com.tytran.blog.exception.AppException;
import com.tytran.blog.exception.ErrorCode;
import com.tytran.blog.mapper.PostMapper;
import com.tytran.blog.repository.PostRepository;
import com.tytran.blog.repository.UserRepository;
import com.tytran.blog.services.PostService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PostServiceImpl implements PostService {

    PostRepository postRepository;

    PostMapper postMapper;

    UserRepository userRepository;

    @Override
    public PostResponseDTO create(PostRequestDTO requestDTO) {
        var context = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByEmail(context.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Posts post = postMapper.toPost(requestDTO);
        post.setUser(user);
        postRepository.save(post);
        return postMapper.toDTO(post);
    }

    @Override
    public List<PostResponseDTO> getAllPost() {
        return postRepository.findAll().stream().map(postMapper::toDTO).toList();
    }

    @Override
    public Boolean delete(UUID id) {
        var context = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByEmail(context.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        Posts post = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        if (!Objects.equals(user.getId(), post.getUser().getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        postRepository.delete(post);
        return true;
    }

    @Override
    public PostResponseDTO update(UUID id, PostRequestDTO requestDTO) {
        Posts post = postRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND));
        var context = SecurityContextHolder.getContext().getAuthentication();
        Users user = userRepository.findByEmail(context.getName())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        if (!Objects.equals(user.getId(), post.getUser().getId())) {
            throw new AppException(ErrorCode.UNAUTHORIZED);
        }
        postMapper.updatePost(post, requestDTO);
        postRepository.save(post);
        return postMapper.toDTO(post);
    }

}
