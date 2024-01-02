package me.kimsuyong.springbootdeveloper.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import me.kimsuyong.springbootdeveloper.domain.User;
import me.kimsuyong.springbootdeveloper.dto.AddUserRequest;
import me.kimsuyong.springbootdeveloper.repository.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public Long save(AddUserRequest dto) {
		return userRepository.save(User.builder()
			.email(dto.getEmail())
			.password(bCryptPasswordEncoder.encode(dto.getPassword()))
			.build()).getId();
	}
}
