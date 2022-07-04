package ru.prooftechit.smh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.prooftechit.smh.api.dto.profile.ProfileRequestDto;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper {

    UserDto toDto(User user);

    @Mapping(target = "email", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "lastActivityAt", ignore = true)
    @Mapping(target = "photo", ignore = true)
    User toUser(ProfileRequestDto request, @MappingTarget User user);
}
