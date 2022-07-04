package ru.prooftechit.smh.mapper;

import org.mapstruct.InheritConfiguration;
import org.mapstruct.Mapper;
import ru.prooftechit.smh.api.dto.documents.DocumentNodeDto;
import ru.prooftechit.smh.api.dto.documents.DocumentNodeWithParentsDto;
import ru.prooftechit.smh.domain.model.FileSystemNode;

/**
 * @author Roman Zdoronok
 */
@Mapper(componentModel = "spring", uses = FileMapper.class)
public interface DocumentTreeMapper {
    DocumentNodeDto toDto(FileSystemNode fileSystemNode);

    @InheritConfiguration
    DocumentNodeWithParentsDto toDtoWithParents(FileSystemNode fileSystemNode);
}
