package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.mapper.IUserRequestMapper;
import com.pragma.powerup.application.mapper.IUserResponseMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @Mock
    private IUserResponseMapper userResponseMapper;

    @InjectMocks
    private UserHandler userHandler;

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void saveUser_AllValidationsPassed() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setNombre("test");
        userRequestDto.setApellido("test");
        userRequestDto.setNumeroDocumento("123456789");
        userRequestDto.setCelular("+5723456789");
        userRequestDto.setFechaNacimiento(LocalDate.of(2005, 1, 1));
        userRequestDto.setClave("123");
        userRequestDto.setCorreo("test@gmail.com");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void saveUser_WhenIsBlankName() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setNombre("");
        userRequestDto.setApellido("test");
        userRequestDto.setNumeroDocumento("123456789");
        userRequestDto.setCelular("+5723456789");
        userRequestDto.setFechaNacimiento(LocalDate.of(2005, 1, 1));
        userRequestDto.setClave("123");
        userRequestDto.setCorreo("test@gmail.com");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        ConstraintViolation<UserRequestDto> violation = violations.iterator().next();
        assertEquals("El nombre no pueda estar vacio", violation.getMessage());
    }

    @Test
    void saveUser_WhenEmailInvalid() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setNombre("test");
        userRequestDto.setApellido("test");
        userRequestDto.setNumeroDocumento("123456789");
        userRequestDto.setCelular("+5723456789");
        userRequestDto.setFechaNacimiento(LocalDate.of(2005, 1, 1));
        userRequestDto.setClave("123");
        userRequestDto.setCorreo("invalid.com");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        ConstraintViolation<UserRequestDto> violation = violations.iterator().next();
        assertEquals("Correo inválido", violation.getMessage());
    }

    @Test
    void saveUserWithPhoneExceedingMaxLengthFailsValidation() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setNombre("test");
        userRequestDto.setApellido("test");
        userRequestDto.setNumeroDocumento("123456789");
        userRequestDto.setCelular("+5730056983250");
        userRequestDto.setFechaNacimiento(LocalDate.of(2005, 1, 1));
        userRequestDto.setClave("123");
        userRequestDto.setCorreo("test@gmail.com");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        ConstraintViolation<UserRequestDto> violation = violations.iterator().next();
        assertEquals("Teléfono inválido. Máx 13 caracteres y puede iniciar con +", violation.getMessage());
    }

    @Test
    void saveUserWithValidPhone() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setNombre("test");
        userRequestDto.setApellido("test");
        userRequestDto.setNumeroDocumento("123456789");
        userRequestDto.setCelular("+573193889249");
        userRequestDto.setFechaNacimiento(LocalDate.of(2005, 1, 1));
        userRequestDto.setClave("123");
        userRequestDto.setCorreo("test@gmail.com");

        Set<ConstraintViolation<UserRequestDto>> violations = validator.validate(userRequestDto);

        assertTrue(violations.isEmpty());
    }

}