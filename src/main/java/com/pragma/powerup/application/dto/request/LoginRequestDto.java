package com.pragma.powerup.application.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequestDto {

    @NotBlank(message = "El correo no puede estar vacio")
    @Email(message = "Correo inválido")
    private String correo;

    @NotBlank(message = "La clave no puede estar vacia")
    private String clave;

}
