package ru.prooftechit.smh.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.api.dto.user.UserRequestDto;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.configuration.swagger.Tags;
import ru.prooftechit.smh.constants.ApiPaths;
import ru.prooftechit.smh.domain.model.User_;

import java.util.Set;

/**
 * @author Roman Zdoronok
 */
@Tag(name = Tags.USER, description = "API работы с пользователями")
@RequestMapping(ApiPaths.USERS)
public interface UserApi {

    @Operation(summary = "Получить пользователя", tags = Tags.USER)
    @GetMapping(path = ApiPaths.USERS_ID_PART)
    ResponseEntity<UserDto> getUser(@PathVariable Long userId);

    @Operation(summary = "Изменить данные пользователя", tags = Tags.USER)
    @PostMapping(path = ApiPaths.USERS_ID_PART)
    ResponseEntity<UserDto> updateUser(@PathVariable Long userId, @RequestBody @Valid UserRequestDto user);

    @Operation(summary = "Изменить роль пользователя", tags = Tags.USER)
    @PatchMapping(path = ApiPaths.USERS_ID_ROLE_PART)
    ResponseEntity<UserDto> updateUserRole(@PathVariable Long userId, @PathVariable UserRole role);

    @Operation(summary = "Удалить пользователя", tags = Tags.USER)
    @DeleteMapping(ApiPaths.USERS_ID_PART)
    ResponseEntity<?> deleteUser(
        @Parameter(description = "Идентификатор пользователя", required = true, example = "1")
        @PathVariable Long userId);

    @Operation(summary = "Список всех пользователей", tags = Tags.USER)
    @GetMapping
    Page<UserDto> getUsers(
        @Parameter(description = "Поисковый запрос", required = false, example = "Иванов")
        @RequestParam(required = false) String search,
        @Parameter(description = "Роли", required = false, example = "reader")
        @RequestParam(required = false) Set<UserRole> roles,
        @PageableDefault(sort = User_.CREATED_AT, direction = Direction.ASC, size = 20) Pageable pageable);
}
