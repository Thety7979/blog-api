package com.tytran.blog.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tytran.blog.entity.Posts;

@Repository
public interface PostRepository extends JpaRepository<Posts, UUID>{
    Optional<Posts> findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Posts p WHERE p.user.id = :id")
    void deletePostByUserId(@Param("id") UUID id);
}
