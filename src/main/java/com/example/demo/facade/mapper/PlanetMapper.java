package com.example.demo.facade.mapper;

import com.example.demo.domain.Planet;
import com.example.demo.domain.User;
import com.example.demo.facade.dto.AdminUserDTO;
import com.example.demo.facade.dto.PlanetDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", uses = {})
public interface PlanetMapper extends EntityMapper<PlanetDTO, Planet> {

    PlanetDTO toDto(Planet planet);

}
