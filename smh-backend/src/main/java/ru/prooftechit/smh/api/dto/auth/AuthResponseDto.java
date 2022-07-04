package ru.prooftechit.smh.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import ru.prooftechit.smh.api.dto.auth.payload.AuthorizedUserInfo;

/**
 * Успешный результат аутентификации.
 *
 * @author Roman Zdoronok
 */
@Schema(description = "Результат успешной авторизации пользователя")
@Data
@AllArgsConstructor
public class AuthResponseDto {
    @Schema(description = "Access токен")
    private String accessToken;
    @Schema(description = "Refresh токен")
    private String refreshToken;
    @Schema(description = "Дополнительная информация о пользователе")
    private AuthorizedUserInfo userInfo;
}
