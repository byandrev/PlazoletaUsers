package com.pragma.powerup.infrastructure.out.jpa.adapter;

import com.pragma.powerup.domain.model.RolModel;
import com.pragma.powerup.domain.model.RolType;
import com.pragma.powerup.domain.spi.IRolPersistencePort;
import com.pragma.powerup.infrastructure.exception.NoDataFoundException;
import com.pragma.powerup.infrastructure.out.jpa.entity.RolEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.IRolEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IRolRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class RolJpaAdapter implements IRolPersistencePort {

    private final IRolRepository rolRepository;
    private final IRolEntityMapper rolEntityMapper;


    @Override
    public RolModel save(RolModel rol) {
        RolEntity rolEntity = rolRepository.save(rolEntityMapper.toEntity(rol));
        return rolEntityMapper.toRolModel(rolEntity);
    }

    @Override
    public List<RolModel> getAll() {
        List<RolEntity> roleEntityList = rolRepository.findAll();

        if (roleEntityList.isEmpty()) {
            throw new NoDataFoundException();
        }

        return rolEntityMapper.toRolModelList(roleEntityList);
    }

    @Override
    public RolModel getByName(RolType name) {
        RolEntity rolEntity = rolRepository.findFirstByNombre(name);

        return rolEntityMapper.toRolModel(rolEntity);
    }
}
