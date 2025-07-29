package org.example.bankcards.exception.custom_exceptions;

public class CardNotFoundException extends RuntimeException {

    public CardNotFoundException() {
        super();
    }

    public CardNotFoundException(String message) {
        super(message);
    }

    public static CardNotFoundException getCardNotFoundException() {
        return new CardNotFoundException("Карта не найдена");
    }
}

