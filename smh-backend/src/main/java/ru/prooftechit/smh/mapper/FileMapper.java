package ru.prooftechit.smh.mapper;

import org.mapstruct.Mapper;
import ru.prooftechit.smh.api.dto.FileDto;
import ru.prooftechit.smh.domain.model.File;

/**
 * @author Roman Zdoronok
 */
@Mapper(componentModel = "spring", uses = CommonMapper.class)
public interface FileMapper {
    FileDto toDto(File file);
    File toEntity(FileDto dto);
}
