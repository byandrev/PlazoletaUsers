package com.pragma.powerup.application.handler.impl;

import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.response.JwtResponseDto;
import com.pragma.powerup.application.handler.IAuthHandler;
import com.pragma.powerup.domain.spi.IAuthPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthHandler implements IAuthHandler {

    private final IAuthPort authPort;

    @Override
    public JwtResponseDto login(LoginRequestDto loginRequestDto) {
        String token = authPort.login(loginRequestDto.getCorreo(), loginRequestDto.getClave());
        return JwtResponseDto.builder().jwt(token).build();
    }

}
