package ru.prooftechit.smh.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * Запрос на обновление токенов.
 *
 * @author Roman Zdoronok
 */
@Schema(description = "Запрос на обновление пары access/refresh токенов")
@Data
public class RefreshRequestDto {
    @Schema(description = "Refresh токен")
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private String refreshToken;
}
