package ru.prooftechit.smh.mapper;

import org.mapstruct.*;
import ru.prooftechit.smh.api.dto.UserEventDto;
import ru.prooftechit.smh.domain.model.UserEvent;
import ru.prooftechit.smh.domain.model.metadata.UserEventMetadata;

@Mapper(componentModel = "spring", uses = {CommonMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEventMapper {
    UserEventDto toDto(UserEvent userEvent, @Context UserEventMetadata metadata);
    UserEvent toEntity(UserEventDto userEventDto, @MappingTarget UserEvent userEvent);

    @AfterMapping
    default void mapMetadata(UserEvent userEvent, @Context UserEventMetadata metadata, @MappingTarget UserEventDto target) {
        target.setRead(metadata != null && metadata.isRead());
    }
}
