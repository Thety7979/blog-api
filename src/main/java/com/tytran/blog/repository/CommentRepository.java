package com.tytran.blog.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tytran.blog.entity.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, UUID>{

}
