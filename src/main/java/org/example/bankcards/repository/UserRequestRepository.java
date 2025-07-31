package org.example.bankcards.repository;

import org.example.bankcards.entity.UserRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс {@code UserRequestRepository} предоставляет методы для работы с таблицей запросов пользователей.
 */
public interface UserRequestRepository extends JpaRepository<UserRequestEntity, Long> {
}
