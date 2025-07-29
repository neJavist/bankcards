package org.example.bankcards.repository;

import org.example.bankcards.entity.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository<CardEntity, Long> {
    Optional<CardEntity> findByIdAndUserName(Long id, String userName);
}





