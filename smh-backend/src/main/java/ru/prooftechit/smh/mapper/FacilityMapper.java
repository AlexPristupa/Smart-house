package ru.prooftechit.smh.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.prooftechit.smh.api.dto.FacilityDto;
import ru.prooftechit.smh.api.dto.FacilityRequestDto;
import ru.prooftechit.smh.domain.model.Facility;

/**
 * @author Roman Zdoronok
 */
@Mapper(componentModel = "spring")
public interface FacilityMapper {

    FacilityDto toDto(Facility facility);

    @Mapping(target = "photo", ignore = true)
    @Mapping(target = "images", ignore = true)
    Facility toEntity(FacilityRequestDto facilityRequestDto);

    @InheritConfiguration
    Facility toEntity(FacilityRequestDto facilityRequestDto, @MappingTarget Facility facility);
}
