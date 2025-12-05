package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.mapper.IUserRequestMapper;
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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserHandlerTest {

    @Mock
    private IUserServicePort userServicePort;

    @Mock
    private IUserRequestMapper userRequestMapper;

    @InjectMocks
    private UserHandler userHandler;

    private UserRequestDto validUserRequestDto;
    private UserModel userModel;

    @BeforeEach
    void setUp() {
        validUserRequestDto = new UserRequestDto();
        validUserRequestDto.setNombre("test");
        validUserRequestDto.setApellido("test");
        validUserRequestDto.setNumeroDocumento("123456789");
        validUserRequestDto.setCelular("+5723456789");
        validUserRequestDto.setFechaNacimiento(LocalDate.of(2005, 1, 1));
        validUserRequestDto.setClave("123");
        validUserRequestDto.setCorreo("test@gmail.com");

        userModel = new UserModel();
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

}