package org.example.bankcards.repository;

import org.example.bankcards.entity.CardTransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardTransferRepository extends JpaRepository<CardTransferEntity, Long> {
}


