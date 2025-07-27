package org.example.bankcards.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.UserDto;
import org.example.bankcards.exception.custom_exceptions.UserNotFoundException;
import org.example.bankcards.mapper.UserMapper;
import org.example.bankcards.repository.UserRepository;
import org.example.bankcards.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с пользователями.
 * Класс предоставляет методы для создания, удаления, обновления и получения информации о пользователях.
 * @see UserMapper - маппер для преобразования между DTO и сущностями.
 * @see PasswordEncoder - кодировщик паролей для шифрования паролей перед сохранением.
 *
 * @see UserService
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Создаёт нового пользователя на основе переданных данных.
     * Пароль пользователя шифруется перед сохранением.
     *
     * @param userDto данные пользователя в формате DTO {@link UserDto}
     * @return сохранённый пользователь в виде DTO {@link UserDto}
     * @throws RuntimeException если произошла ошибка при обработке {@link RuntimeException}
     */
    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        return Optional.of(userDto)
                .map(user -> {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                    return user;
                })
                .map(userMapper::toEntity)
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow();
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор удаляемого пользователя {@link Long}
     * @throws UserNotFoundException если пользователь не найден {@link UserNotFoundException}
     */
    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete, this::getUserNotFoundException);
    }

    /**
     * Обновляет информацию существующего пользователя.
     *
     * @param userDto обновлённые данные пользователя в формате DTO {@link UserDto}
     * @return обновлённый пользователь в виде DTO {@link UserDto}
     * @throws UserNotFoundException если пользователь не найден {@link UserNotFoundException}
     */
    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto) {
        return userRepository.findById(userDto.getId())
                .map(entity -> userMapper.mergeToEntity(userDto, entity))
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(this::getUserNotFoundException);
    }

    /**
     * Получает информацию о пользователе по его идентификатору.
     *
     * @param id идентификатор пользователя {@link Long}
     * @return информация о пользователе в виде DTO {@link UserDto}
     * @throws UserNotFoundException если пользователь не найден {@link UserNotFoundException}
     */
    @Transactional(readOnly = true)
    @Override
    public UserDto getUser(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(this::getUserNotFoundException);
    }

    /**
     * Получает информацию о пользователе по его имени.
     *
     * @param username имя пользователя {@link String}
     * @return информация о пользователе в виде DTO {@link UserDto}
     * @throws UserNotFoundException если пользователь не найден {@link UserNotFoundException}
     */
    @Transactional(readOnly = true)
    @Override
    public UserDto getUserByUsername(String username) {
        return userRepository.findUserByName(username)
                .map(userMapper::toDto)
                .orElseThrow(this::getUserNotFoundException);
    }

    /**
     * Получает список всех пользователей.
     *
     * @return список пользователей в виде DTO {@link UserDto}
     */
    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    private UserNotFoundException getUserNotFoundException() {
        return new UserNotFoundException("User not found");
    }
}

