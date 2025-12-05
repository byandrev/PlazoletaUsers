package com.pragma.powerup.application.dto.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class LoginRequestDtoValidationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private LoginRequestDto createValidLoginDto() {
        return LoginRequestDto.builder()
                .correo("user@example.com")
                .clave("123")
                .build();
    }

    private void assertViolation(LoginRequestDto dto, String expectedMessage) {
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Debería haber violaciones de validación.");

        boolean found = violations.stream()
                .anyMatch(v -> v.getMessage().equals(expectedMessage));

        assertTrue(found, "No se encontró el mensaje de violación esperado: " + expectedMessage);
    }

    @Test
    @DisplayName("Login válido debe pasar la validación")
    void loginValidateEmailAndPass_Success() {
        LoginRequestDto dto = createValidLoginDto();
        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "No deberían existir violaciones con datos válidos.");
    }

    @Test
    @DisplayName("Correo vacío debe fallar la validación")
    void loginValidateEmail_Empty_FailsValidation() {
        LoginRequestDto dto = createValidLoginDto();
        dto.setCorreo("");
        assertViolation(dto, "El correo no puede estar vacio");
    }

    @Test
    @DisplayName("Clave vacía debe fallar la validación")
    void loginValidatePassword_Empty_FailsValidation() {
        LoginRequestDto dto = createValidLoginDto();
        dto.setClave("");
        assertViolation(dto, "La clave no puede estar vacia");
    }

    @Test
    @DisplayName("Correo con formato inválido debe fallar la validación")
    void loginValidateEmail_InvalidFormat_FailsValidation() {
        LoginRequestDto dto = createValidLoginDto();
        dto.setCorreo("invalid-email");

        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
    }

}
