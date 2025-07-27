package org.example.bankcards.exception.custom_exceptions;

/**
 * Исключение, выбрасываемое при попытке получить информацию о пользователе, который не существует.
 * Используется для обработки ситуаций, когда пользователь с указанным идентификатором или именем не найден в системе.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Создаёт новое исключение без сообщения.
     */
    public UserNotFoundException() {
        super();
    }

    /**
     * Создаёт новое исключение с пользовательским сообщением.
     *
     * @param message текстовое сообщение, описывающее причину ошибки {@link String}
     */
    public UserNotFoundException(String message) {
        super(message);
    }
}

