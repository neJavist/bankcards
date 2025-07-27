package org.example.bankcards.repository;

import org.example.bankcards.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link CardEntity}.
 * Предоставляет стандартные методы для поиска, сохранения, удаления и получения информации о банковских картах.
 */
public interface CardRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByCardNumber(String cardNumber);
}





