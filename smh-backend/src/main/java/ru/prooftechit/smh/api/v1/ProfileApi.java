package ru.prooftechit.smh.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import ru.prooftechit.smh.api.dto.RequestFiles;
import ru.prooftechit.smh.api.dto.profile.ChangePasswordRequestDto;
import ru.prooftechit.smh.api.dto.profile.ProfileRequestDto;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.configuration.swagger.Tags;
import ru.prooftechit.smh.constants.ApiPaths;

/**
 * @author Roman Zdoronok
 */
@Tag(name = Tags.PROFILE, description = "API работы с профилем пользователя")
@RequestMapping(ApiPaths.PROFILE)
public interface ProfileApi {

    @Operation(summary = "Получить данные профиля", tags = Tags.PROFILE)
    @GetMapping
    ResponseEntity<UserDto> getProfile();

    @Operation(summary = "Обновить данные профиля", tags = Tags.PROFILE)
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<UserDto> updateProfile(@RequestPart @Valid ProfileRequestDto profile,
                                          @Valid RequestFiles requestFiles);

    @Operation(summary = "Изменить пароль", tags = Tags.PROFILE)
    @PostMapping(ApiPaths.PROFILE_CHANGE_PASSWORD_PART)
    ResponseEntity<?> changePassword(@RequestBody @Valid ChangePasswordRequestDto password);

}
