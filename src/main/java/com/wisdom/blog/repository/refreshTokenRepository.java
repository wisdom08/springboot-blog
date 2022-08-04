package com.wisdom.blog.repository;

import com.wisdom.blog.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface refreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(String userId);

    void deleteByUserId(String name);
}
