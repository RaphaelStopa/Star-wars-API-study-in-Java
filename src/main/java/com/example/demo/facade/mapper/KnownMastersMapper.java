package com.example.demo.facade.mapper;

import com.example.demo.domain.KnownMaster;
import com.example.demo.facade.dto.KnownMasterDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {ForceUserMapper.class})
public interface KnownMastersMapper extends EntityMapper<KnownMasterDTO, KnownMaster> {

    KnownMasterDTO toDto(KnownMaster knownMaster);
}
