package com.pragma.powerup.domain.exception;

public class RolNotFound extends RuntimeException {
    public RolNotFound() {
        super("El rol no existe");
    }
}
