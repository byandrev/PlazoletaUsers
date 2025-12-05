package com.pragma.powerup.domain.exception;

import org.springframework.http.HttpStatus;

public class UserNotFound extends DomainException {
    public UserNotFound() {
        super("Usuario no encontrado", HttpStatus.NOT_FOUND.value());
    }
}
