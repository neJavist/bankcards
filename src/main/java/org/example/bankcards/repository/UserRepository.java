package org.example.bankcards.repository;

import org.example.bankcards.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link UserEntity}.
 * Предоставляет методы для поиска, сохранения, удаления и получения информации о пользователях.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Ищет пользователя по его имени.
     *
     * @param name имя пользователя {@link String}
     * @return опциональный объект пользователя, если он найден {@link UserEntity}
     */
    Optional<UserEntity> findUserByName(String name);
}
