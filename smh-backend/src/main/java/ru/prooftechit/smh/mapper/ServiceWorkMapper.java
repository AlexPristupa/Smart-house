package ru.prooftechit.smh.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.prooftechit.smh.api.dto.ServiceWorkDto;
import ru.prooftechit.smh.api.dto.ServiceWorkRequestDto;
import ru.prooftechit.smh.api.enums.ServiceWorkResolution;
import ru.prooftechit.smh.domain.model.ServiceWork;

/**
 * @author Roman Zdoronok
 */
@Mapper(componentModel = "spring", uses = {CommonMapper.class, ServiceWorkTypeMapper.class})
public interface ServiceWorkMapper {
    ServiceWorkDto toDto(ServiceWork serviceWork);

    @Mapping(target = "type", ignore = true)
    ServiceWork toEntity(ServiceWorkRequestDto serviceWorkRequestDto);

    @AfterMapping
    default ServiceWork afterMappingServiceWork(ServiceWorkRequestDto serviceWorkRequestDto, @MappingTarget ServiceWork serviceWork) {
        if (serviceWorkRequestDto.getResolution() == null) {
            serviceWork.setResolution(ServiceWorkResolution.UNRESOLVED);
        }
        return serviceWork;
    }

    @InheritConfiguration
    ServiceWork toEntity(ServiceWorkRequestDto serviceWorkRequestDto, @MappingTarget ServiceWork serviceWork);
}
