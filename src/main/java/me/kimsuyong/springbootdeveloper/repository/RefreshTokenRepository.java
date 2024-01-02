package me.kimsuyong.springbootdeveloper.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import me.kimsuyong.springbootdeveloper.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByUserId(Long userId);

	Optional<RefreshToken> findRefreshToken(String refreshToken);

}
