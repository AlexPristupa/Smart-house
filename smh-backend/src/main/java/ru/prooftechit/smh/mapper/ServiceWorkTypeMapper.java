package ru.prooftechit.smh.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.prooftechit.smh.api.dto.ServiceWorkTypeDto;
import ru.prooftechit.smh.api.dto.ServiceWorkTypeRequestDto;
import ru.prooftechit.smh.domain.model.ServiceWorkType;
import ru.prooftechit.smh.domain.repository.ServiceWorkRepository;

/**
 * @author Roman Zdoronok
 */
@Mapper(componentModel = "spring", uses = {CommonMapper.class})
public interface ServiceWorkTypeMapper {

    ServiceWorkTypeDto toDto(ServiceWorkType serviceWorkType, @Context ServiceWorkRepository serviceWorkRepository);
    ServiceWorkType toEntity(ServiceWorkTypeRequestDto serviceWorkTypeRequestDto);
    ServiceWorkType toEntity(ServiceWorkTypeRequestDto serviceWorkTypeRequestDto,
                             @MappingTarget ServiceWorkType serviceWorkType);

    @AfterMapping
    default ServiceWorkTypeDto afterMapping(ServiceWorkType serviceWorkType,
                                            @MappingTarget ServiceWorkTypeDto serviceWorkTypeDto,
                                            @Context ServiceWorkRepository serviceWorkRepository) {
        serviceWorkTypeDto.setLinked(serviceWorkRepository.existsByTypeId(serviceWorkTypeDto.getId()));
        return serviceWorkTypeDto;
    }
}
