package com.tytran.blog.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tytran.blog.entity.Posts;

@Repository
public interface PostRepository extends JpaRepository<Posts, UUID>{
    
}
