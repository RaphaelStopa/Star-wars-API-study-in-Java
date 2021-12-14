package com.example.demo.facade.mapper;

import com.example.demo.domain.Planet;
import com.example.demo.domain.TypeForceUser;
import com.example.demo.facade.dto.PlanetDTO;
import com.example.demo.facade.dto.TypeForceUserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface TypeForceUserMapper extends EntityMapper<TypeForceUserDTO, TypeForceUser>{


    TypeForceUserDTO toDto(TypeForceUser typeForceUser);
}
