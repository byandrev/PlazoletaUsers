package com.pragma.powerup.domain.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends DomainException {
    public InvalidCredentialsException() {
        super("Credenciales Invalidas", HttpStatus.BAD_REQUEST.value());
    }
}
