package com.pragma.powerup.domain.spi;

import com.pragma.powerup.domain.model.RolModel;
import com.pragma.powerup.domain.model.RolType;

import java.util.List;

public interface IRolPersistencePort {

    RolModel save(RolModel rol);

    List<RolModel> getAll();

    RolModel getByName(RolType name);

}
