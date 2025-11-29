package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.RolModel;
import com.pragma.powerup.domain.model.RolType;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRolPersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolPersistencePort rolPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    @Override
    public void saveUser(UserModel user) {
        RolModel propietarioRole = rolPersistencePort.getByName(RolType.PROPIETARIO);
        String hashedPassword = passwordEncoderPort.encode(user.getClave());

        user.setClave(hashedPassword);
        user.setRol(propietarioRole);

        userPersistencePort.saveUser(user);
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }

    @Override
    public UserModel getUserById(Long id) {
        return userPersistencePort.getUserById(id);
    }
}
