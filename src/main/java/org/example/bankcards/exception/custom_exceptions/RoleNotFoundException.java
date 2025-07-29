package org.example.bankcards.exception.custom_exceptions;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException() {
        super();
    }

    public RoleNotFoundException(String message) {
        super(message);
    }

    public static RoleNotFoundException getRoleNotFoundException() {
        return new RoleNotFoundException("Роль не найдена");
    }
}

