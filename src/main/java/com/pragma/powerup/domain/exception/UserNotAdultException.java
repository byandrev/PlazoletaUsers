package com.pragma.powerup.domain.exception;

public class UserNotAdultException extends DomainException {
    public UserNotAdultException() {
        super("El usuario debe ser mayor de edad");
    }
}
