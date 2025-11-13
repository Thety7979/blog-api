package com.tytran.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tytran.blog.entity.InvalidatedToken;

@Repository
public interface InvalidatedTokenRepository extends JpaRepository<InvalidatedToken, String> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM invalidated_token WHERE expirytime < NOW() - INTERVAL '1 day'", nativeQuery = true)
    void deleteExpiriedToken();
}
