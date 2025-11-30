package com.pragma.powerup.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserModelTest {

    @Test
    void testUserModelGettersAndSetters() {
        Long id = 1L;
        String nombre = "Andres";
        String apellido = "Parra";
        String numeroDocumento = "1092940348";
        String celular = "+573001234567";
        LocalDate fechaNacimiento = LocalDate.of(2003, 9, 29);
        String correo = "byandrev@gmail.com";
        String clave = "password123";
        RolModel rol = new RolModel(1L, RolType.ADMINISTRADOR, "Admin Role");

        UserModel userModel = new UserModel();

        userModel.setId(id);
        userModel.setNombre(nombre);
        userModel.setApellido(apellido);
        userModel.setNumeroDocumento(numeroDocumento);
        userModel.setCelular(celular);
        userModel.setFechaNacimiento(fechaNacimiento);
        userModel.setCorreo(correo);
        userModel.setClave(clave);
        userModel.setRol(rol);

        assertEquals(id, userModel.getId());
        assertEquals(nombre, userModel.getNombre());
        assertEquals(apellido, userModel.getApellido());
        assertEquals(numeroDocumento, userModel.getNumeroDocumento());
        assertEquals(celular, userModel.getCelular());
        assertEquals(fechaNacimiento, userModel.getFechaNacimiento());
        assertEquals(correo, userModel.getCorreo());
        assertEquals(clave, userModel.getClave());
        assertEquals(rol, userModel.getRol());
    }

    @Test
    void testUserModelAllArgsConstructor() {
        Long id = 1L;
        String nombre = "Andres";
        String apellido = "Parra";
        String numeroDocumento = "1092940348";
        String celular = "+573001234567";
        LocalDate fechaNacimiento = LocalDate.of(2003, 9, 29);
        String correo = "byandrev@gmail.com";
        String clave = "password123";
        RolModel rol = new RolModel(1L, RolType.ADMINISTRADOR, "Admin Role");

        UserModel userModel = new UserModel(id, nombre, apellido, numeroDocumento, celular, fechaNacimiento, correo, clave, rol);

        assertEquals(id, userModel.getId());
        assertEquals(nombre, userModel.getNombre());
        assertEquals(apellido, userModel.getApellido());
        assertEquals(numeroDocumento, userModel.getNumeroDocumento());
        assertEquals(celular, userModel.getCelular());
        assertEquals(fechaNacimiento, userModel.getFechaNacimiento());
        assertEquals(correo, userModel.getCorreo());
        assertEquals(clave, userModel.getClave());
        assertEquals(rol, userModel.getRol());
    }

    @Test
    void testUserModelNoArgsConstructor() {
        UserModel userModel = new UserModel();

        assertNull(userModel.getId());
        assertNull(userModel.getNombre());
        assertNull(userModel.getApellido());
        assertNull(userModel.getNumeroDocumento());
        assertNull(userModel.getCelular());
        assertNull(userModel.getFechaNacimiento());
        assertNull(userModel.getCorreo());
        assertNull(userModel.getClave());
        assertNull(userModel.getRol());
    }
}
