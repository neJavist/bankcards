package org.example.bankcards.exception.custom_exceptions;

/**
 * Исключение, выбрасываемое при попытке получить информацию о карте, которая не существует.
 * Используется для обработки ситуаций, когда карта с указанным идентификатором не найдена в системе.
 */
public class CardNotFoundException extends RuntimeException {

    /**
     * Создаёт новое исключение без сообщения.
     */
    public CardNotFoundException() {
        super();
    }

    /**
     * Создаёт новое исключение с пользовательским сообщением.
     *
     * @param message текстовое сообщение, описывающее причину ошибки {@link String}
     */
    public CardNotFoundException(String message) {
        super(message);
    }
}

