package org.example.bankcards.repository;

import org.example.bankcards.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью {@link RoleEntity}.
 * Предоставляет стандартные методы для поиска, сохранения, удаления и получения информации о ролях.
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}

