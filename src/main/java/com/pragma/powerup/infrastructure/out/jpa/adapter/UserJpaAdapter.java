package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.exception.UserNotFound;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class UserJpaAdapter implements IUserPersistencePort {

    private final IUserRepository userRepository;
    private final IUserEntityMapper userEntityMapper;


    @Override
    public UserModel saveUser(UserModel user) {
        UserEntity userEntity = userRepository.save(userEntityMapper.toEntity(user));
        return userEntityMapper.toUserModel(userEntity);
    }

    @Override
    public List<UserModel> getAllUsers() {
        List<UserEntity> usersEntityList = userRepository.findAll();
        return userEntityMapper.toUserModelList(usersEntityList);
    }

    @Override
    public UserModel getUserById(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(UserNotFound::new);
        return userEntityMapper.toUserModel(userEntity);
    }

    @Override
    public UserModel getByCorreo(String correo) {
        UserEntity userEntity = userRepository.findByCorreo(correo).orElseThrow(UserNotFound::new);
        return userEntityMapper.toUserModel(userEntity);
    }
}
