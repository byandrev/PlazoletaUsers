package com.pragma.powerup.infrastructure.exceptionhandler;

public enum ExceptionResponse {
    NO_DATA_FOUND("No data found for the requested petition"),
    DUPLICATE_DATA("Ya existe un registro con estos datos en el sistema"),
    BAD_REQUEST("Bad Request"),
    VALIDATION_ERROR("Error en el formato de los datos"),
    JSON_ERROR("Error de formato de JSON"),
    SERVER_ERROR("Internal Server Error");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}