package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.JwtResponseDto;
import com.pragma.powerup.domain.spi.IAuthPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthHandlerTest {

    @Mock
    private IAuthPort authPort;

    @InjectMocks
    private AuthHandler authHandler;

    private LoginRequestDto loginRequestDto;
    private final String expectedJwt = "jwtsecret";

    @BeforeEach
    void setUp() {
        loginRequestDto = LoginRequestDto
                .builder()
                .correo("test@gmail.com")
                .clave("12345")
                .build();
    }

    @Test
    @DisplayName("login debe llamar al puerto de auth y devolver el JWT")
    void login_SuccessfulAuthentication_ReturnsJwt() {
        when(authPort.login(loginRequestDto.getCorreo(), loginRequestDto.getClave()))
                .thenReturn(expectedJwt);

        JwtResponseDto jwtCurrent = authHandler.login(loginRequestDto);

        verify(authPort).login(loginRequestDto.getCorreo(), loginRequestDto.getClave());

        assertNotNull(jwtCurrent);
        assertEquals(expectedJwt, jwtCurrent.getJwt());

        verifyNoMoreInteractions(authPort);
    }

    @Test
    @DisplayName("login debe propagar excepción si el puerto falla (credenciales inválidas)")
    void login_InvalidCredentials_ThrowsException() {
        doThrow(new RuntimeException("Credenciales inválidas")).when(authPort)
                .login(loginRequestDto.getCorreo(), loginRequestDto.getClave());

        assertThrows(RuntimeException.class, () -> authHandler.login(loginRequestDto),
                "El handler debe propagar la excepción de autenticación fallida.");

        verify(authPort).login(loginRequestDto.getCorreo(), loginRequestDto.getClave());
    }

}
