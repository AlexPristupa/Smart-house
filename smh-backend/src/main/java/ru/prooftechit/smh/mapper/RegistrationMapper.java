package ru.prooftechit.smh.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import ru.prooftechit.smh.api.dto.registration.UserRegistrationRequest;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.domain.model.User;

@Mapper(componentModel = "spring", uses = {CommonMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RegistrationMapper {
    @Mapping(target = "password", ignore = true)
    User toUser(UserRegistrationRequest userRegistrationRequest);
}
