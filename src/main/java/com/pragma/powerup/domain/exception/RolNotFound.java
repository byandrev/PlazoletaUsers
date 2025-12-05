package com.pragma.powerup.domain.exception;

import org.springframework.http.HttpStatus;

public class RolNotFound extends DomainException {
    public RolNotFound() {
        super("El rol no existe", HttpStatus.NOT_FOUND.value());
    }
}
