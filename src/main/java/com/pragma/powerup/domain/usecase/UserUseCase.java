package com.pragma.powerup.domain.usecase;

import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.exception.UserNotAdultException;
import com.pragma.powerup.domain.model.RolModel;
import com.pragma.powerup.domain.model.RolType;
import com.pragma.powerup.domain.model.UserModel;
import com.pragma.powerup.domain.spi.IPasswordEncoderPort;
import com.pragma.powerup.domain.spi.IRolPersistencePort;
import com.pragma.powerup.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserUseCase implements IUserServicePort {

    private final IUserPersistencePort userPersistencePort;
    private final IRolPersistencePort rolPersistencePort;
    private final IPasswordEncoderPort passwordEncoderPort;

    private boolean isOfLegalAge(LocalDate dateOfBirth) {
        return Period.between(dateOfBirth, LocalDate.now()).getYears() >= 18;
    }

    @Override
    public void saveUser(UserModel user) {
        if (!isOfLegalAge(user.getFechaNacimiento())) {
            throw new UserNotAdultException();
        }

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
