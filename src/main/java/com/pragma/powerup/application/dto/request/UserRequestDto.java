package com.pragma.powerup.application.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDto {

    private String nombre;

    private String apellido;

    @Pattern(
        regexp = "^[0-9]+$",
        message = "El documento debe contener solo números"
    )
    private String numeroDocumento;

    @Pattern(
        regexp = "^\\+?\\d{1,13}$",
        message = "Teléfono inválido. Máx 13 dígitos y puede iniciar con +"
    )
    private String celular;

    private LocalDate fechaNacimiento;

    @Email(message = "Correo inválido")
    private String correo;

    private String clave;

    private String rol;

}
