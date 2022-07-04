package ru.prooftechit.smh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.prooftechit.smh.api.dto.SessionDto;
import ru.prooftechit.smh.domain.model.Session;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface SessionMapper {

    @Mapping(target = "active", expression = "java(session != null && session.getExpireAt().isAfter(java.time.Instant.now()))")
    SessionDto toDto(Session session);
}
