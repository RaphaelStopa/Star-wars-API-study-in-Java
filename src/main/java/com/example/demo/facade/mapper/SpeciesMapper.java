package com.example.demo.facade.mapper;

import com.example.demo.domain.Species;
import com.example.demo.facade.dto.SpeciesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface SpeciesMapper extends EntityMapper<SpeciesDTO, Species>  {

    SpeciesDTO toDto(Species species);
}
