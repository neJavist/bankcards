package org.example.bankcards.security.service;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.UserDto;
import org.example.bankcards.dto.jwt.JwtAuthenticationResponseDto;
import org.example.bankcards.dto.jwt.SignInRequestDto;
import org.example.bankcards.dto.jwt.SignUpRequestDto;
import org.example.bankcards.exception.custom_exceptions.UserNotFoundException;
import org.example.bankcards.mapper.UserMapper;
import org.example.bankcards.repository.UserRepository;
import org.example.bankcards.security.jwt.JwtService;
import org.example.bankcards.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    public JwtAuthenticationResponseDto signUp(SignUpRequestDto request) {

        UserDto user = UserDto.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("ROLE_ADMIN")
                .build();

        userService.createUser(user);

        String jwt = jwtService.generateToken(userMapper.toEntity(user));
        return new JwtAuthenticationResponseDto(jwt);
    }

    public JwtAuthenticationResponseDto signIn(SignInRequestDto request) {
        UserDetails user = userRepository.findUserByName(request.getUsername())
                .orElseThrow(UserNotFoundException::getUserNotFoundException);

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseDto(jwt);
    }
}
