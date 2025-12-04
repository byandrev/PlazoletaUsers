package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.dto.response.UserResponseDto;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.infrastructure.input.rest.response.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class UserRestController {

    private final IUserHandler userHandler;

    @Operation(summary = "Add a new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content)
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @PostMapping("/")
    public ResponseEntity<Void> saveUser(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.saveUser(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All users returned"),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
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

    @Operation(summary = "Get user by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User returned"),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<UserResponseDto>> getById(@PathVariable Long id) {
        CustomResponse<UserResponseDto> response = CustomResponse.<UserResponseDto>builder()
                .status(HttpStatus.OK.value())
                .data(userHandler.getUserById(id))
                .build();

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Add a new employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Employee created", content = @Content),
            @ApiResponse(responseCode = "409", description = "Employee already exists", content = @Content)
    })
    @PreAuthorize("hasRole('PROPIETARIO')")
    @PostMapping("/employee")
    public ResponseEntity<Void> saveEmployee(@Valid @RequestBody UserRequestDto userRequestDto) {
        userHandler.saveEmployee(userRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
