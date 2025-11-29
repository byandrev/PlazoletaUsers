package com.pragma.powerup.infrastructure.out.jpa.repository;

import com.pragma.powerup.domain.model.RolType;
import com.pragma.powerup.infrastructure.out.jpa.entity.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRolRepository extends JpaRepository<RolEntity, Long> {
    List<RolEntity> findByNombre(RolType nombre);

    RolEntity findFirstByNombre(RolType nombre);
}
