package com.pragma.powerup.infrastructure.security.authentication;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final IUserPersistencePort userPersistencePort;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserModel userModel = userPersistencePort.getByCorreo(username);

        String rol = String.valueOf(userModel.getRol().getNombre());
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + rol));

        return CustomUserDetail.builder()
                .id(userModel.getId())
                .email(userModel.getCorreo())
                .password(userModel.getClave())
                .authorities(authorities)
                .build();
    }

}
