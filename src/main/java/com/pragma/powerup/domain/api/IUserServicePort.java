package com.pragma.powerup.domain.api;

import com.pragma.powerup.domain.model.UserModel;

import java.util.List;

public interface IUserServicePort {

    UserModel saveUser(UserModel user);

    List<UserModel> getAllUsers();

    UserModel getUserById(Long id);

}
