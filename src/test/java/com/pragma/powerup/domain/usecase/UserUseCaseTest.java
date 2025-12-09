package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.exception.UserNotAdultException;
import com.pragma.powerup.domain.model.RolModel;
import com.pragma.powerup.domain.model.RolType;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRolPersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
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

    private UserModel buildUserModel(Long id, String nombre) {
        return UserModel
                .builder()
                .id(id)
                .nombre(nombre)
                .correo("andres@gmail.com")
                .apellido("Parra")
                .celular("+573197821102")
                .numeroDocumento("11321312")
                .fechaNacimiento(LocalDate.of(2003, 9, 29))
                .clave("123")
                .build();
    }

    @Test
    @DisplayName("getAll debe retornar todos los usuarios")
    void getAllUsers_Paged_shouldReturnList() {
        UserModel user1 = buildUserModel(1L, "User1");
        UserModel user2 = buildUserModel(2L, "User2");

        List<UserModel> usersList = Arrays.asList(user1, user2);

        when(userPersistencePort.getAllUsers()).thenReturn(usersList);

        List<UserModel> result = userUseCase.getAllUsers();

        assertNotNull(result, "La lista no debe ser null.");
        assertEquals(2, result.size(), "Debe retornar el tamaño de la página (2).");
        assertEquals("User1", result.get(0).getNombre(), "El primer usuario debe ser 'User'.");
        assertEquals("User2", result.get(1).getNombre(), "El segundo usuario debe ser 'User2'.");

        verify(userPersistencePort).getAllUsers();
    }

    @Test
    @DisplayName("getById debe retornar un usuario")
    void getUserById_shouldReturnUser_Found() {
        Long userId = 1L;
        UserModel expectedUser = buildUserModel(userId, "User1");

        when(userPersistencePort.getUserById(userId)).thenReturn(expectedUser);

        assertDoesNotThrow(() -> {
            UserModel result = userUseCase.getUserById(userId);

            assertNotNull(result, "El resultado no debe ser null.");
            assertEquals(userId, result.getId(), "El ID debe coincidir.");
            assertEquals("User1", result.getNombre());
        }, "No deberia lanzar una excepcion");

        verify(userPersistencePort).getUserById(userId);
    }

    @Test
    @DisplayName("save no debe generar ninguna excepcion")
    void saveUser_success() {
        UserModel newUser = buildUserModel(1L, "Test");
        RolModel rol = RolModel.builder().id(1L).nombre(RolType.PROPIETARIO).build();
        String hashedPassword = "123";

        when(rolPersistencePort.getByName(rol.getNombre())).thenReturn(rol);
        when(passwordEncoderPort.encode(newUser.getClave())).thenReturn(hashedPassword);

        newUser.setRol(rol);
        newUser.setClave(hashedPassword);

        assertDoesNotThrow(() -> userUseCase.saveUser(newUser), "No debe lanzar excepción");

        verify(rolPersistencePort).getByName(rol.getNombre());
        verify(passwordEncoderPort).encode(newUser.getClave());
        verify(userPersistencePort).saveUser(newUser);
    }

    @Test
    @DisplayName("save debe lanzar una excepcion cuando usuario sea menor de edad")
    void saveUser_WhenUserIsUnderage_ShouldThrowException() {
        UserModel userModel = buildUserModel(1L, "Test");
        userModel.setFechaNacimiento(LocalDate.now().minusYears(17));

        UserNotAdultException exception = assertThrows(UserNotAdultException.class, () -> {
            userUseCase.saveUser(userModel);
        });

        assertEquals("El usuario debe ser mayor de edad", exception.getMessage());
        verify(userPersistencePort, never()).saveUser(any(UserModel.class));
    }
    
    @Test
    @DisplayName("save debe lanzar una excepcion cuando usuario no tenga rol")
    void saveEmployee_WithoutRole_ShouldThrowException() {
        UserModel userModel = buildUserModel(1L, "Test");

        assertThrows(NullPointerException.class, () -> {
            userUseCase.saveUser(userModel);
        });

        verify(userPersistencePort, never()).saveUser(any(UserModel.class));
    }

}
