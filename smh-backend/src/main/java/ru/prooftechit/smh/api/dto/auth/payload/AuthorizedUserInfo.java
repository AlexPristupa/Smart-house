package ru.prooftechit.smh.api.dto.auth.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.api.enums.UserStatus;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Информация об авторизованном пользователе")
@Getter
@Setter
@Accessors(chain = true)
public class AuthorizedUserInfo {
    @Schema(description = "Почта")
    private String email;
    @Schema(description = "Статус пользователя")
    private UserStatus status;
    @Schema(description = "Специфическая информация о пользователе, исходя из его роли")
    private RoleFeatures roleFeatures;
}
