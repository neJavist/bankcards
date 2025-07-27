package org.example.bankcards.exception.custom_exceptions;

public class NegativeBalanceException extends RuntimeException{
    public NegativeBalanceException() {
        super();
    }

    public NegativeBalanceException(String message) {
        super(message);
    }
}
