package com.pragma.powerup.application.mapper;

import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.domain.model.RolModel;
import com.pragma.powerup.domain.model.RolType;
import com.pragma.powerup.domain.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    unmappedSourcePolicy = ReportingPolicy.IGNORE
)
public interface IUserRequestMapper {

    @Mapping(target = "rol", source = "rol", qualifiedByName = "stringToRolModel")
    UserModel toUser(UserRequestDto userRequestDto);

    @Named("stringToRolModel")
    default RolModel stringToRolModel(String rol) {
        if (rol == null) {
            return null;
        }

        RolModel rolModel = new RolModel();
        rolModel.setNombre(RolType.valueOf(rol.toUpperCase()));
        return rolModel;
    }

}
