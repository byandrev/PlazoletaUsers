package com.pragma.powerup.infrastructure.security.authentication;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserPersistencePort userPersistencePort;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserModel userModel = userPersistencePort.getByCorreo(username);

        return User.builder()
                .username(userModel.getCorreo())
                .password(userModel.getClave())
                .roles(String.valueOf(userModel.getRol().getNombre()))
                .build();
    }

}
