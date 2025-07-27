package org.example.bankcards.exception.custom_exceptions;

/**
 * Исключение, выбрасываемое при попытке получить информацию о роли, которая не существует.
 * Используется для обработки ситуаций, когда роль с указанным идентификатором не найдена в системе.
 */
public class RoleNotFoundException extends RuntimeException {

    /**
     * Создаёт новое исключение без сообщения.
     */
    public RoleNotFoundException() {
        super();
    }

    /**
     * Создаёт новое исключение с пользовательским сообщением.
     *
     * @param message текстовое сообщение, описывающее причину ошибки {@link String}
     */
    public RoleNotFoundException(String message) {
        super(message);
    }
}

