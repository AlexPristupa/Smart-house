package ru.prooftechit.smh.mapper;

import org.mapstruct.Mapper;
import ru.prooftechit.smh.api.dto.NamedWithDescriptionDto;
import ru.prooftechit.smh.domain.model.common.NamedWithDescriptionEntity;

/**
 * @author Roman Zdoronok
 */
@Mapper(componentModel = "spring")
public interface BaseMapper {
    NamedWithDescriptionDto toDto(NamedWithDescriptionEntity<?> entity);
}
