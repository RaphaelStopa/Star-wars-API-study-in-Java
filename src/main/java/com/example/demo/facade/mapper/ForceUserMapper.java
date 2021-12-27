package com.example.demo.facade.mapper;

import com.example.demo.domain.ForceUser;
import com.example.demo.domain.Planet;
import com.example.demo.facade.dto.ForceUserDTO;
import com.example.demo.facade.dto.PlanetDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PlanetMapper.class, SpeciesMapper.class})
public interface ForceUserMapper extends EntityMapper<ForceUserDTO, ForceUser> {
}
