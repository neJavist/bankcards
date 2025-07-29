package org.example.bankcards.service;

import org.example.bankcards.dto.CardDto;

import java.util.List;

public interface CardService {

    CardDto createCard(CardDto cardDto, Long userId);

    void deleteCard(Long cardId);

    CardDto updateCard(CardDto cardDto);

    CardDto getCard(Long cardId);

    List<CardDto> getAllCards();

    List<CardDto> getAllUserCards(String username);

    String getBalance(Long cardId, String username);
}
