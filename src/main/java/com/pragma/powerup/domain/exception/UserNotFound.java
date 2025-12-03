package com.pragma.powerup.domain.exception;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("Usuario no encontrado");
    }
}
