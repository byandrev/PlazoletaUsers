package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IUserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import lombok.RequiredArgsConstructor;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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
        
        if (usersEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }

        return userEntityMapper.toUserModelList(usersEntityList);
    }

    @Override
    public UserModel getUserById(Long id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        
        if (userEntity.isEmpty()) throw new NoDataFoundException();
        
        return userEntityMapper.toUserModel(userEntity.get());
    }

    @Override
    public UserModel getByCorreo(String correo) {
        Optional<UserEntity> userEntity = userRepository.findByCorreo(correo);

        if (userEntity.isEmpty()) throw new NoDataFoundException();

        return userEntityMapper.toUserModel(userEntity.get());
    }
}
