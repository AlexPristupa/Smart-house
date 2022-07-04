package ru.prooftechit.smh.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.prooftechit.smh.api.dto.HardwareDto;
import ru.prooftechit.smh.api.dto.HardwareRequestDto;
import ru.prooftechit.smh.domain.model.Hardware;

/**
 * @author Roman Zdoronok
 */
@Mapper(componentModel = "spring", uses = CommonMapper.class)
public interface HardwareMapper {
    HardwareDto toDto(Hardware hardware);

    @Mapping(target = "photo", ignore = true)
    @Mapping(target = "images", ignore = true)
    Hardware toEntity(HardwareRequestDto hardwareRequestDto);

    @InheritConfiguration
    Hardware toEntity(HardwareRequestDto hardwareRequestDto, @MappingTarget Hardware hardware);
}
