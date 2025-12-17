package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.infrastructure.input.rest.response.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Usuarios", description = "Endpoints para la gestión de usuarios")
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(
            summary = "Crear un nuevo Propietario",
            description = "Permite a un administrador registrar un usuario con el rol de PROPIETARIO. **Solo accesible por ADMINISTRADOR.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Propietario creado exitosamente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Datos de entrada inválidos", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para crear propietarios", content = @Content),
            @ApiResponse(responseCode = "409", description = "El usuario (correo o documento) ya existe", content = @Content)
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/")
    public ResponseEntity<Void> saveUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.saveUser(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Retorna una lista completa de todos los usuarios registrados en el sistema. **Solo accesible por ADMINISTRADOR.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Listado de usuarios obtenido"),
            @ApiResponse(responseCode = "404", description = "No se encontraron usuarios registrados", content = @Content)
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/")
    public ResponseEntity<CustomResponse<List<UserResponseDto>>> getAll() {
        CustomResponse<List<UserResponseDto>> response = CustomResponse.<List<UserResponseDto>>builder()
                .status(HttpStatus.OK.value())
                .data(userHandler.getAllUsers())
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Obtener usuario por ID",
            description = "Busca y retorna la información de perfil de un usuario específico mediante su identificador único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "404", description = "El ID de usuario no existe", content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<UserResponseDto>> getById(
            @Parameter(description = "ID único del usuario", example = "1")
            @PathVariable Long id
    ) {
        CustomResponse<UserResponseDto> response = CustomResponse.<UserResponseDto>builder()
                .status(HttpStatus.OK.value())
                .data(userHandler.getUserById(id))
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(
            summary = "Crear un nuevo empleado",
            description = "Permite a un propietario registrar a sus empleados. **Solo accesible por PROPIETARIO.**"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empleado creado exitosamente", content = @Content),
            @ApiResponse(responseCode = "403", description = "No tiene permisos para registrar empleados", content = @Content),
            @ApiResponse(responseCode = "409", description = "El empleado ya se encuentra registrado", content = @Content)
    })
    @PreAuthorize("hasRole('PROPIETARIO')")
    @PostMapping("/employee")
    public ResponseEntity<Void> saveEmployee(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.saveEmployee(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
