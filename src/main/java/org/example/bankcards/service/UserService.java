package org.example.bankcards.service;

import org.example.bankcards.dto.UserDto;

import java.util.List;

/**
 * Интерфейс содержит методы для управления пользователями в системе.
 */
public interface UserService {

    /**
     * Создаёт нового пользователя на основе переданных данных.
     *
     * @param userDto данные пользователя в формате DTO
     * @return созданный пользователь в виде объекта {@link UserDto}
     */
    UserDto createUser(UserDto userDto);

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя, которого нужно удалить
     */
    void deleteUser(Long userId);

    /**
     * Обновляет информацию о существующем пользователе.
     *
     * @param userDto новые данные пользователя в формате DTO
     * @param id      идентификатор пользователя, которого нужно обновить
     * @return обновлённый пользователь в виде объекта {@link UserDto}
     */
    UserDto updateUser(UserDto userDto, Long id);

    /**
     * Получает пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return пользователь в виде объекта {@link UserDto}
     */
    UserDto getUser(Long userId);

    /**
     * Получает список всех пользователей.
     *
     * @return список пользователей в виде объектов {@link UserDto}
     */
    List<UserDto> getAllUsers();
}
