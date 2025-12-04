package com.pragma.powerup.infrastructure.out.security.adapter;

import com.pragma.powerup.domain.exception.InvalidCredentialsException;
import com.pragma.powerup.domain.spi.IAuthPort;
import com.pragma.powerup.infrastructure.security.authentication.CustomUserDetail;
import com.pragma.powerup.infrastructure.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuthAdapter implements IAuthPort {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public String login(String correo, String clave) {
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(correo, clave);

        try {
            Authentication authentication = authenticationManager.authenticate(login);

            CustomUserDetail user = (CustomUserDetail) authentication.getPrincipal();
            String rol = authentication.getAuthorities().iterator().next().getAuthority();

            return jwtUtils.generateToken(user.getId(), correo, rol);
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }
    }

}
