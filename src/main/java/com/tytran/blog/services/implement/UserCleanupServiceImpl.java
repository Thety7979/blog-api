package com.tytran.blog.services.implement;

import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tytran.blog.repository.CommentRepository;
import com.tytran.blog.repository.PostRepository;
import com.tytran.blog.services.UserCleanupService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserCleanupServiceImpl implements UserCleanupService{

    PostRepository postRepository;

    CommentRepository commentRepository;

    @Override
    @Transactional
    public void cleanupUserData(UUID userId) {
        commentRepository.deleteCommentByUserId(userId);
        postRepository.deletePostByUserId(userId);
    }
    
}
