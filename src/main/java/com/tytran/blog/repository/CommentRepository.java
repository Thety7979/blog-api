package com.tytran.blog.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tytran.blog.entity.Comments;

@Repository
public interface CommentRepository extends JpaRepository<Comments, UUID> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Comments c WHERE c.user.id = :id")
    void deleteCommentByUserId(@Param("id") UUID id);
}
