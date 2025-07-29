package org.example.bankcards.exception.custom_exceptions;

public class CardIsNotActiveException extends RuntimeException{

    public CardIsNotActiveException() {
        super();
    }

    public CardIsNotActiveException(String message) {
        super(message);
    }

    public static CardIsNotActiveException getCardIsNotActiveException() {
        return new CardIsNotActiveException("Карта не активна");
    }
}
