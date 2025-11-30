package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDto {

    @NotBlank(message = "El nombre no pueda estar vacio")
    private String nombre;

    @NotBlank(message = "El apellido no pueda estar vacio")
    private String apellido;

    @NotBlank(message = "El numero de documento no pueda estar vacio")
    @Pattern(
        regexp = "^[0-9]+$",
        message = "El documento debe contener solo números"
    )
    private String numeroDocumento;

    @NotBlank(message = "El telefono no pueda estar vacio")
    @Pattern(
        regexp = "^(\\+\\d{1,12}|\\d{1,13})$",
        message = "Teléfono inválido. Máx 13 caracteres y puede iniciar con +"
    )
    private String celular;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El correo no puede estar vacio")
    @Email(message = "Correo inválido")
    private String correo;

    @NotBlank(message = "La clave no puede estar vacia")
    private String clave;

    private String rol;

}
