package ru.prooftechit.smh.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * Запрос на аутентификацию
 *
 * @author Roman Zdoronok
 */
@Schema(description = "Авторизационные данные")
@Data
public class AuthRequestDto {
    @Schema(description = "Логин", required = true)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private String username;

    @Schema(description = "Пароль", required = true)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private String password;

    @Schema(description = "Небезопасный компьютер")
    private boolean insecurePc;
}
