package com.pragma.powerup.infrastructure.configuration;

import com.pragma.powerup.application.handler.IAuthHandler;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.handler.impl.AuthHandler;
import com.pragma.powerup.application.handler.impl.UserHandler;
import com.pragma.powerup.application.mapper.IUserRequestMapper;
import com.pragma.powerup.application.mapper.IUserResponseMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.spi.IAuthPort;
import com.pragma.powerup.domain.spi.IBCryptPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRolPersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.domain.usecase.UserUseCase;
import com.pragma.powerup.infrastructure.out.bcrypt.adapter.BCryptPasswordEncoderAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.RolJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.adapter.UserJpaAdapter;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRolEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRolRepository;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import com.pragma.powerup.infrastructure.out.security.adapter.AuthAdapter;
import com.pragma.powerup.infrastructure.security.PasswordEncoderAdapter;
import com.pragma.powerup.infrastructure.security.authentication.CustomUserDetailsService;
import com.pragma.powerup.infrastructure.security.jwt.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;

@Configuration
public class BeanConfiguration {

    @Bean
    public IUserPersistencePort userPersistencePort(IUserRepository userRepository, IUserEntityMapper userEntityMapper) {
        return new UserJpaAdapter(userRepository, userEntityMapper);
    }

    @Bean
    public IRolPersistencePort rolPersistencePort(IRolRepository rolRepository, IRolEntityMapper rolEntityMapper) {
        return new RolJpaAdapter(rolRepository, rolEntityMapper);
    }

    @Bean
    public IAuthPort authPort(AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        return new AuthAdapter(authenticationManager, jwtUtils);
    }

    @Bean
    public IBCryptPasswordEncoderPort bCryptPasswordEncoderPort() {
        return new BCryptPasswordEncoderAdapter();
    }

    @Bean
    public IPasswordEncoderPort passwordEncoderPort(IBCryptPasswordEncoderPort bCryptPasswordEncoderPort) {
        return new PasswordEncoderAdapter(bCryptPasswordEncoderPort);
    }

    @Bean
    public IUserServicePort userServicePort(IUserPersistencePort userPersistencePort,
                                            IRolPersistencePort rolPersistencePort,
                                            IPasswordEncoderPort passwordEncoderPort) {
        return new UserUseCase(userPersistencePort, rolPersistencePort, passwordEncoderPort);
    }

    @Bean
    public IUserHandler userHandler(IUserServicePort userServicePort,
                                    IUserRequestMapper userRequestMapper,
                                    IUserResponseMapper userResponseMapper) {
        return new UserHandler(userServicePort, userRequestMapper, userResponseMapper);
    }

    @Bean
    public IAuthHandler authHandler(IAuthPort authPort) {
        return new AuthHandler(authPort);
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService(IUserPersistencePort userPersistencePort) {
        return new CustomUserDetailsService(userPersistencePort);
    }

}
