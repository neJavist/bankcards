package org.example.bankcards.exception.custom_exceptions;

public class CardIsNotActiveException extends RuntimeException{
    public CardIsNotActiveException() {
        super();
    }

    public CardIsNotActiveException(String message) {
        super(message);
    }
}
