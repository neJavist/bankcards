package org.example.bankcards.repository;

import org.example.bankcards.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Интерфейс {@code UserRepository} предоставляет методы для работы с таблицей пользователей в базе данных.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Находит пользователя по его имени.
     *
     * @param name имя пользователя
     * @return {@code Optional<UserEntity>} — опциональный объект, содержащий найденного пользователя или пустое значение
     */
    Optional<UserEntity> findUserByName(String name);
}
