package org.example.bankcards.exception.custom_exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super();
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public static UserNotFoundException getUserNotFoundException() {
        return new UserNotFoundException("Пользователь не найден");
    }
}

