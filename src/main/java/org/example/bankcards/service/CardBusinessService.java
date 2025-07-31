package org.example.bankcards.service;

import org.example.bankcards.dto.CardDto;
import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.dto.UserRequestDto;

/**
 * Интерфейс содержит методы для управления состоянием карт и операциями с ними.
 */
public interface CardBusinessService {

    /**
     * Метод блокировки карты по её идентификатору.
     *
     * @param id идентификатор карты, которую необходимо заблокировать
     * @return обновлённая карта в виде объекта {@link CardDto}
     */
    CardDto blockCard(Long id);

    /**
     * Метод активации карты по её идентификатору.
     *
     * @param id идентификатор карты, которую необходимо активировать
     * @return обновлённая карта в виде объекта {@link CardDto}
     */
    CardDto activateCard(Long id);

    /**
     * Метод перевода средств между картами.
     * <p>
     * Выполняет перевод заданной суммы с карты отправителя на карту получателя.
     *
     * @param cardTransferDto данные о переводе (номера карт, сумма)
     * @param username        имя пользователя, совершающего перевод
     * @return объект {@link CardTransferDto}, представляющий выполненный перевод
     */
    CardTransferDto transfer(CardTransferDto cardTransferDto, String username);

    /**
     * Метод отправки запроса на блокировку карты от имени пользователя.
     * <p>
     * Позволяет пользователю отправить запрос на блокировку своей карты.
     *
     * @param id       идентификатор карты, которую нужно заблокировать
     * @param username имя пользователя, отправляющего запрос
     * @return объект {@link UserRequestDto}, представляющий созданный запрос
     */
    UserRequestDto userBlockRequest(Long id, String username);
}
