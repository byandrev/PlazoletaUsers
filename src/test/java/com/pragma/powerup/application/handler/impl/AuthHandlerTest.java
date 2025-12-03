package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.JwtResponseDto;
import com.pragma.powerup.domain.spi.IAuthPort;
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
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthHandlerTest {

    @Mock
    private IAuthPort authPort;

    @InjectMocks
    private AuthHandler authHandler;

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void loginValidateEmailAndPasswordSuccess() {
        LoginRequestDto loginRequestDto = LoginRequestDto
                .builder()
                .correo("test@gmail.com")
                .clave("12345")
                .build();

        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(loginRequestDto);

        assertTrue(violations.isEmpty());
    }

    @Test
    void loginValidateEmailFailsValidation() {
        LoginRequestDto loginRequestDto = LoginRequestDto
                .builder()
                .correo("")
                .clave("12345")
                .build();

        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(loginRequestDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        ConstraintViolation<LoginRequestDto> violation = violations.iterator().next();
        assertEquals("El correo no puede estar vacio", violation.getMessage());
    }


    @Test
    void loginValidatePasswordFailsValidation() {
        LoginRequestDto loginRequestDto = LoginRequestDto
                .builder()
                .correo("test@gmail.com")
                .clave("")
                .build();

        Set<ConstraintViolation<LoginRequestDto>> violations = validator.validate(loginRequestDto);

        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        ConstraintViolation<LoginRequestDto> violation = violations.iterator().next();
        assertEquals("La clave no puede estar vacia", violation.getMessage());
    }

    @Test
    void loginValidateSuccess() {
        LoginRequestDto loginRequestDto = LoginRequestDto
                .builder()
                .correo("test@gmail.com")
                .clave("12345")
                .build();

        JwtResponseDto jwtResponseDto = JwtResponseDto.builder().jwt("jwt").build();

        when(authPort.login(loginRequestDto.getCorreo(), loginRequestDto.getClave()))
                .thenReturn(jwtResponseDto.getJwt());

        JwtResponseDto jwtCurrent = authHandler.login(loginRequestDto);

        verify(authPort).login(loginRequestDto.getCorreo(), loginRequestDto.getClave());
        assertEquals("jwt", jwtCurrent.getJwt());
    }

}
