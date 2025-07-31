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

/**
 * Класс {@code AuthenticationService} отвечает за обработку процессов регистрации и входа в систему.
 * Он использует сервисы шифрования паролей, генерации токенов и работу с пользователями.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    /**
     * Метод регистрации нового пользователя.
     * <p>
     * Создаёт нового пользователя на основе переданных данных и генерирует JWT-токен.
     *
     * @param request данные пользователя для регистрации
     * @return объект с JWT-токеном и сообщением об успешной регистрации
     */
    public JwtAuthenticationResponseDto signUp(SignUpRequestDto request) {

        UserDto user = UserDto.builder()
                .name(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .role("ROLE_ADMIN")
                .build();

        userService.createUser(user);

        String jwt = jwtService.generateToken(userMapper.toEntity(user));
        return new JwtAuthenticationResponseDto(jwt);
    }

    /**
     * Метод авторизации существующего пользователя.
     * <p>
     * Проверяет учетные данные пользователя и возвращает JWT-токен.
     *
     * @param request данные пользователя для входа
     * @return объект с JWT-токеном и сообщением об успешной авторизации
     * @throws UserNotFoundException если пользователь не найден
     * @throws RuntimeException      если введён неверный пароль
     */
    public JwtAuthenticationResponseDto signIn(SignInRequestDto request) {
        UserDetails user = userRepository.findUserByName(request.getUsername())
                .orElseThrow(UserNotFoundException::getUserNotFoundException);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Неправильный пароль");
        }

        String jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseDto(jwt);
    }
}
