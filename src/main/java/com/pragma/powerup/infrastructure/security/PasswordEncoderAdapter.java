package com.pragma.powerup.infrastructure.security;

import com.pragma.powerup.domain.spi.IBCryptPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordEncoderAdapter implements IPasswordEncoderPort {

    private final IBCryptPasswordEncoderPort ibCryptPasswordEncoderPort;

    @Override
    public String encode(String rawPassword) {
        return ibCryptPasswordEncoderPort.encode(rawPassword);
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return ibCryptPasswordEncoderPort.matches(rawPassword, encodedPassword);
    }
}
