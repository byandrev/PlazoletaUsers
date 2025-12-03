package com.pragma.powerup.domain.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Credenciales Invalidas");
    }
}
