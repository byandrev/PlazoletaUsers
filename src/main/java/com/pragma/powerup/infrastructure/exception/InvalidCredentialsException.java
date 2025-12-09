package com.pragma.powerup.infrastructure.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends InfraException {
    public InvalidCredentialsException() {
        super("Credenciales Invalidas", HttpStatus.BAD_REQUEST.value());
    }
}
