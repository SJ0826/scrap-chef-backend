package com.example.scrap_chef.repository;

import com.example.scrap_chef.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {

    @Query(value = "SELECT * FROM user_refresh_token p WHERE user_id = :userId LIMIT 1", nativeQuery = true)
    Optional<UserRefreshToken> findByUserId(@Param("userId") Long userId);

    Optional<UserRefreshToken> findByRefreshToken(String refreshToken);
}
