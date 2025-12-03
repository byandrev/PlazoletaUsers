package com.pragma.powerup.infrastructure.input.rest;

import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.JwtResponseDto;
import com.pragma.powerup.application.handler.IAuthHandler;
import com.pragma.powerup.infrastructure.input.rest.response.CustomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthHandler  authHandler;

    @Operation(summary = "Login user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged", headers = {
                @Header(name = "Authorization", description = "JWT token", schema = @Schema(type = "string"))
            }),
            @ApiResponse(responseCode = "400", description = "Bad credentials", content = @Content),
            @ApiResponse(responseCode = "404", description = "No data found", content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<CustomResponse<JwtResponseDto>> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
        JwtResponseDto jwt = authHandler.login(loginRequestDto);

        CustomResponse<JwtResponseDto> response = CustomResponse.<JwtResponseDto>builder()
                .status(HttpStatus.OK.value())
                .data(JwtResponseDto.builder().jwt(jwt.getJwt()).build())
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwt)
                .body(response);
    }

}
