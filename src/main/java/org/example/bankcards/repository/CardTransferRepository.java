package org.example.bankcards.repository;

import org.example.bankcards.entity.CardTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Интерфейс {@code CardTransferRepository} предоставляет методы для работы с таблицей перевода средств в базе данных.
 */
public interface CardTransferRepository extends JpaRepository<CardTransferEntity, Long> {
}


