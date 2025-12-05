package com.pragma.powerup.domain.exception;

import org.springframework.http.HttpStatus;

public class UserNotAdultException extends DomainException {
    public UserNotAdultException() {
        super("El usuario debe ser mayor de edad", HttpStatus.BAD_REQUEST.value());
    }
}
