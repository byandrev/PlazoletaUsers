package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.UserNotAdultException;
import com.pragma.powerup.domain.model.RolModel;
import com.pragma.powerup.domain.model.RolType;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRolPersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort userPersistencePort;

    @Mock
    private IRolPersistencePort rolPersistencePort;

    @Mock
    private IPasswordEncoderPort passwordEncoderPort;

    @InjectMocks
    private UserUseCase userUseCase;

    @Test
    void saveUser_WhenUserIsUnderage_ShouldThrowException() {
        UserModel userModel = new UserModel();
        userModel.setFechaNacimiento(LocalDate.now().minusYears(17));

        UserNotAdultException exception = assertThrows(UserNotAdultException.class, () -> {
            userUseCase.saveUser(userModel);
        });

        assertEquals("El usuario debe ser mayor de edad", exception.getMessage());
        verify(userPersistencePort, never()).saveUser(any(UserModel.class));
    }

    @Test
    void saveUser_WhenUserIsAdult_ShouldSaveUser() {
        UserModel userModel = new UserModel();
        userModel.setFechaNacimiento(LocalDate.now().minusYears(18));
        userModel.setClave("password");

        RolModel rolModel = new RolModel();
        rolModel.setNombre(RolType.PROPIETARIO);

        when(rolPersistencePort.getByName(RolType.PROPIETARIO)).thenReturn(rolModel);
        when(passwordEncoderPort.encode("password")).thenReturn("encodedPassword");

        userUseCase.saveUser(userModel);

        verify(userPersistencePort).saveUser(userModel);
        assertEquals("encodedPassword", userModel.getClave());
        assertEquals(rolModel, userModel.getRol());
    }

    @Test
    void saveEmployeeSuccess() {
        RolModel rolModel = new RolModel();
        rolModel.setNombre(RolType.EMPLEADO);

        UserModel userModel = new UserModel();
        userModel.setFechaNacimiento(LocalDate.now().minusYears(18));
        userModel.setClave("password");
        userModel.setRol(rolModel);

        when(rolPersistencePort.getByName(RolType.EMPLEADO)).thenReturn(rolModel);
        when(passwordEncoderPort.encode("password")).thenReturn("encodedPassword");

        userUseCase.saveUser(userModel);

        verify(userPersistencePort).saveUser(userModel);
        assertEquals("encodedPassword", userModel.getClave());
        assertEquals(rolModel, userModel.getRol());
    }

    @Test
    void getAllUsers() {
        UserModel user1 = new UserModel();
        user1.setId(1L);
        user1.setNombre("User1");

        UserModel user2 = new UserModel();
        user2.setId(2L);
        user2.setNombre("User2");

        when(userPersistencePort.getAllUsers()).thenReturn(List.of(user1, user2));

        List<UserModel> result = userUseCase.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("User1", result.get(0).getNombre());
        assertEquals("User2", result.get(1).getNombre());
        verify(userPersistencePort).getAllUsers();
    }

    @Test
    void getUserById() {
        UserModel user = new UserModel();
        user.setId(1L);
        user.setNombre("User1");

        when(userPersistencePort.getUserById(1L)).thenReturn(user);

        UserModel result = userUseCase.getUserById(1L);

        assertNotNull(result);
        assertEquals("User1", result.getNombre());
        verify(userPersistencePort).getUserById(1L);
    }
}
