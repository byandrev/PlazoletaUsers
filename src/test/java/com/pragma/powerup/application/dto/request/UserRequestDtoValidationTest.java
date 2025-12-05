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
import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class UserRequestDtoValidationTest {

    private Validator validator;
    private static final LocalDate MIN_AGE_DATE = LocalDate.now().minusYears(18);

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    private UserRequestDto createValidUser() {
        UserRequestDto dto = new UserRequestDto();
        dto.setNombre("NombreTest");
        dto.setApellido("ApellidoTest");
        dto.setNumeroDocumento("1234567890");
        dto.setCelular("+573001234567");
        dto.setFechaNacimiento(MIN_AGE_DATE.minusYears(1));
        dto.setClave("ContrasenaSegura123");
        dto.setCorreo("valido@test.com");
        return dto;
    }

    private void assertViolation(UserRequestDto dto, String fieldName, String expectedMessage) {
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Debería haber violaciones de validación.");

        boolean found = violations.stream()
                .anyMatch(v -> v.getPropertyPath().toString().equals(fieldName) && v.getMessage().equals(expectedMessage));

        assertTrue(found, String.format("No se encontró la violación esperada para el campo '%s' con mensaje '%s'. Total: %d",
                fieldName, expectedMessage, violations.size()));
    }

    @Test
    @DisplayName("Todos los campos válidos deben pasar la validación")
    void allFieldsValid_shouldPass() {
        UserRequestDto dto = createValidUser();
        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty(), "No deberían existir violaciones con datos válidos.");
    }

    @Test
    @DisplayName("Nombre vacío debe fallar")
    void nameBlank_shouldFailValidation() {
        UserRequestDto dto = createValidUser();
        dto.setNombre("");
        assertViolation(dto, "nombre", "El nombre no pueda estar vacio");

        dto.setNombre(null);

        assertFalse(validator.validate(dto).isEmpty());
    }

    @Test
    @DisplayName("Correo inválido debe fallar")
    void emailInvalid_shouldFail() {
        UserRequestDto dto = createValidUser();
        dto.setCorreo("invalid.com");
        assertViolation(dto, "correo", "Correo inválido");
    }

    @Test
    @DisplayName("Celular excede longitud máxima debe fallar")
    void phoneTooLong_shouldFailValidation() {
        UserRequestDto dto = createValidUser();
        dto.setCelular("+5730012345678");
        assertViolation(dto, "celular", "Teléfono inválido. Máx 13 caracteres y puede iniciar con +");
    }

    @Test
    @DisplayName("Celular válido (con y sin +) debe pasar")
    void phoneValid_shouldPass() {
        UserRequestDto dto1 = createValidUser();
        dto1.setCelular("3001234567");
        assertTrue(validator.validate(dto1).isEmpty());

        UserRequestDto dto2 = createValidUser();
        dto2.setCelular("+573001234567");
        assertTrue(validator.validate(dto2).isEmpty());
    }

}