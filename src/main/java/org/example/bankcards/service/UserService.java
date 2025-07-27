package org.example.bankcards.service;

import org.example.bankcards.dto.UserDto;

import java.util.List;

/**
 * Сервисный интерфейс для работы с пользователями.
 * Определяет методы для создания, удаления, обновления и получения информации о пользователях.
 */
public interface UserService {

    /**
     * Создаёт нового пользователя на основе переданных данных.
     *
     * @param userDto данные пользователя в формате DTO {@link UserDto}
     * @return сохранённый пользователь в виде DTO {@link UserDto}
     */
    UserDto createUser(UserDto userDto);

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param userId идентификатор удаляемого пользователя {@link Long}
     */
    void deleteUser(Long userId);

    /**
     * Обновляет информацию существующего пользователя.
     *
     * @param userDto обновлённые данные пользователя в формате DTO {@link UserDto}
     * @return обновлённый пользователь в виде DTO {@link UserDto}
     */
    UserDto updateUser(UserDto userDto);

    /**
     * Получает информацию о пользователе по его идентификатору.
     *
     * @param userId идентификатор пользователя {@link Long}
     * @return информация о пользователе в виде DTO {@link UserDto}
     */
    UserDto getUser(Long userId);

    /**
     * Получает информацию о пользователе по его имени.
     *
     * @param username имя пользователя {@link String}
     * @return информация о пользователе в виде DTO {@link UserDto}
     */
    UserDto getUserByUsername(String username);

    /**
     * Получает список всех пользователей.
     *
     * @return список пользователей в виде DTO {@link UserDto}
     */
    List<UserDto> getAllUsers();
}

