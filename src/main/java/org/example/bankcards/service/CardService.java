package org.example.bankcards.service;

import org.example.bankcards.dto.CardDto;
import org.example.bankcards.dto.CardFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Интерфейс определяет набор методов для работы с банковскими картами.
 */
public interface CardService {

    /**
     * Создаёт новую банковскую карту для указанного пользователя.
     *
     * @param cardDto  данные карты в формате DTO
     * @param userId   идентификатор пользователя, которому принадлежит карта
     * @return созданная карта в виде объекта {@link CardDto}
     */
    CardDto createCard(CardDto cardDto, Long userId);

    /**
     * Удаляет карту по её идентификатору.
     *
     * @param cardId идентификатор карты, которую нужно удалить
     */
    void deleteCard(Long cardId);

    /**
     * Обновляет информацию о существующей карте.
     *
     * @param cardDto обновлённые данные карты в формате DTO
     * @return обновлённая карта в виде объекта {@link CardDto}
     */
    CardDto updateCard(CardDto cardDto);

    /**
     * Получает карту по её идентификатору.
     *
     * @param cardId идентификатор карты
     * @return карта в виде объекта {@link CardDto}
     */
    CardDto getCardById(Long cardId);

    /**
     * Получает список всех карт с поддержкой пагинации.
     *
     * @param pageable параметры пагинации (номер страницы, количество записей)
     * @return страница с картами в виде объектов {@link CardDto}
     */
    Page<CardDto> getAllCardsPaginated(Pageable pageable);

    /**
     * Получает список всех карт, принадлежащих указанному пользователю.
     *
     * @param username имя пользователя
     * @return список карт в виде объектов {@link CardDto}
     */
    List<CardDto> getAllUserCards(String username);

    /**
     * Получает баланс указанной карты.
     *
     * @param cardId   идентификатор карты
     * @param username имя пользователя, которому принадлежит карта
     * @return строковое представление баланса
     */
    String getBalance(Long cardId, String username);

    /**
     * Получает список карт указанного пользователя с поддержкой пагинации.
     *
     * @param username  имя пользователя
     * @param pageable  параметры пагинации (номер страницы, количество записей)
     * @return страница с картами пользователя в виде объектов {@link CardDto}
     */
    Page<CardDto> getAllUserCardsPaged(String username, Pageable pageable);

    /**
     * Получает отфильтрованный список карт указанного пользователя с поддержкой пагинации.
     *
     * @param cardFilterDto критерии фильтрации (например, статус, номер карты, баланс и т.д.)
     * @param username      имя пользователя
     * @param pageable      параметры пагинации (номер страницы, количество записей)
     * @return страница с отфильтрованными картами пользователя в виде объектов {@link CardDto}
     */
    Page<CardDto> getUserCardsFiltredPaged(CardFilterDto cardFilterDto, String username, Pageable pageable);
}
