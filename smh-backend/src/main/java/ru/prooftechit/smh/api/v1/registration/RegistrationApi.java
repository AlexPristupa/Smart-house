package ru.prooftechit.smh.api.v1.registration;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.prooftechit.smh.api.dto.registration.UserRegistrationRequest;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.configuration.swagger.Tags;
import ru.prooftechit.smh.constants.ApiPaths;

/**
 * @author Roman Zdoronok
 */
@Tag(name = Tags.REGISTER, description = "API для работы с регистрацией")
@RequestMapping(ApiPaths.REGISTRATION)
public interface RegistrationApi {

    @Operation(summary = "Зарегистрировать нового пользователя", tags = Tags.REGISTER)
    @PostMapping
    ResponseEntity<UserDto> newUserRegistration(
        @RequestBody @Valid UserRegistrationRequest request);

}
