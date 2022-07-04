package ru.prooftechit.smh.mapper;

import org.mapstruct.*;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.api.dto.user.UserRequestDto;
import ru.prooftechit.smh.domain.model.User;

@Mapper(componentModel = "spring", uses = {CommonMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper {
    UserDto toUserDto(User user);
    User toEntity(UserRequestDto userRequestDto, @MappingTarget User user);
}
