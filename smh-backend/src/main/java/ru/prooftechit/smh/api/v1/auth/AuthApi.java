package ru.prooftechit.smh.api.v1.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.prooftechit.smh.api.dto.auth.AuthRequestDto;
import ru.prooftechit.smh.api.dto.auth.AuthResponseDto;
import ru.prooftechit.smh.api.dto.auth.RefreshRequestDto;
import ru.prooftechit.smh.configuration.swagger.Tags;
import ru.prooftechit.smh.constants.ApiPaths;

/**
 * @author Roman Zdoronok
 */
@Tag(name = Tags.AUTH, description = "API для работы с авторизацией")
@RequestMapping(ApiPaths.AUTH)
public interface AuthApi {

    @Operation(summary = "Получить пару access/refresh токенов, данные о текущем пользователе", tags = Tags.AUTH)
    @PostMapping(ApiPaths.AUTH_LOGIN_PART)
    ResponseEntity<AuthResponseDto> login(
        @Parameter(description = "Авторизационные данные", required = true) @RequestBody @Valid AuthRequestDto requestDto,
        HttpServletRequest request);

    @Operation(summary = "Обновить пару access/refresh токенов", tags = Tags.AUTH)
    @PostMapping(ApiPaths.AUTH_REFRESH_PART)
    ResponseEntity<AuthResponseDto> refresh(
        @Parameter(description = "Refresh токен для получения нового access токена", required = true)
        @RequestBody @Valid RefreshRequestDto requestDto);
}
