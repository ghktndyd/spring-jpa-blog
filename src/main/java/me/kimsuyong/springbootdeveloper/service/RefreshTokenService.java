package me.kimsuyong.springbootdeveloper.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.kimsuyong.springbootdeveloper.domain.RefreshToken;
import me.kimsuyong.springbootdeveloper.repository.RefreshTokenRepository;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

	private final RefreshTokenRepository refreshTokenRepository;

	public RefreshToken findByRefreshToken(String refreshToken) {
		return refreshTokenRepository.findRefreshToken(refreshToken)
			.orElseThrow(() -> new IllegalArgumentException("Unexpected token"));
	}

}
