package org.example.bankcards.service;

import org.example.bankcards.dto.CardDto;
import org.example.bankcards.entity.CardEntity;

import java.util.List;

/**
 * Сервисный интерфейс для работы с банковскими картами.
 * Определяет методы для создания, удаления, обновления и получения информации о картах.
 */
public interface CardService {

    /**
     * Создаёт новую банковскую карту на основе переданных данных.
     *
     * @param cardDto данные карты в формате DTO {@link CardDto}
     * @return сохранённая карта в виде DTO {@link CardDto}
     */
    CardDto createCard(CardDto cardDto);

    /**
     * Удаляет карту по её идентификатору.
     *
     * @param cardId идентификатор удаляемой карты {@link Long}
     */
    void deleteCard(Long cardId);

    /**
     * Обновляет информацию существующей карты.
     *
     * @param cardDto обновлённые данные карты в формате DTO {@link CardDto}
     * @return обновлённая карта в виде DTO {@link CardDto}
     */
    CardDto updateCard(CardDto cardDto);

    /**
     * Получает информацию о карте по её идентификатору.
     *
     * @param cardId идентификатор карты {@link Long}
     * @return информация о карте в виде DTO {@link CardDto}
     */
    CardDto getCard(Long cardId);

    /**
     * Получает список всех карт.
     *
     * @return список карт в виде DTO {@link CardDto}
     */
    List<CardDto> getAllCards();

    /**
     * Получает список карт, принадлежащих пользователю с указанным идентификатором.
     *
     * @param userId идентификатор пользователя {@link Long}
     * @return список карт пользователя в виде DTO {@link CardDto}
     */
    List<CardDto> getAllUserCards(Long userId);

    CardDto getCardByCardNumber(String cardNumber);
}
