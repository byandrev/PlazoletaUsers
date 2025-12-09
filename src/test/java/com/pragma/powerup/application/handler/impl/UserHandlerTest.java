package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;
import com.pragma.powerup.application.mapper.IUserRequestMapper;
import com.pragma.powerup.application.mapper.IUserResponseMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    private UserRequestDto validUserRequestDto;
    private UserModel userModel;
    private UserResponseDto userResponseDto;

    private final Long USER_ID = 1L;

    @BeforeEach
    void setUp() {
        validUserRequestDto = UserRequestDto
                .builder()
                .nombre("Test")
                .apellido("Test")
                .numeroDocumento("123456789")
                .celular("+5723456789")
                .fechaNacimiento(LocalDate.of(2005, 1, 1))
                .clave("123")
                .correo("test@gmail.com")
                .build();

        userModel = UserModel
                .builder()
                .id(USER_ID)
                .build();


        userResponseDto = UserResponseDto
                .builder()
                .id(USER_ID)
                .build();
    }

    @Test
    @DisplayName("saveUser debe llamar al mapper y al puerto de servicio una vez")
    void saveUser_validDto_shouldCallMapperAndService() {
        when(userRequestMapper.toUser(validUserRequestDto)).thenReturn(userModel);

        assertDoesNotThrow(() -> userHandler.saveUser(validUserRequestDto),
                "No debería lanzar excepción con un DTO válido.");

        verify(userRequestMapper).toUser(validUserRequestDto);
        verify(userServicePort).saveUser(userModel);
        verifyNoMoreInteractions(userServicePort);
    }

    @Test
    @DisplayName("saveUser debe propagar excepción si el correo ya existe")
    void saveUser_existingEmail_shouldThrowException() {
        when(userRequestMapper.toUser(validUserRequestDto)).thenReturn(userModel);

        doThrow(new RuntimeException("Usuario existente")).when(userServicePort).saveUser(userModel);

        assertThrows(RuntimeException.class, () -> userHandler.saveUser(validUserRequestDto),
                "Debería lanzar la excepción de la capa de dominio.");

        verify(userRequestMapper).toUser(validUserRequestDto);
        verify(userServicePort).saveUser(userModel);
    }

    @Test
    @DisplayName("getById debe llamar al servicio y mapear el Model a Response DTO")
    void getById_SuccessfulFlow_ReturnsResponseDto() {
        when(userServicePort.getUserById(USER_ID)).thenReturn(userModel);
        when(userResponseMapper.toResponse(userModel)).thenReturn(userResponseDto);

        UserResponseDto result = userHandler.getUserById(USER_ID);

        verify(userServicePort).getUserById(USER_ID);
        verify(userResponseMapper).toResponse(userModel);

        assertNotNull(result);
        assertEquals(USER_ID, result.getId());

        verifyNoMoreInteractions(userServicePort, userRequestMapper, userResponseMapper);
    }

}