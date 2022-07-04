package ru.prooftechit.smh.api.dto.auth.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.api.enums.UserRole;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Специфическая информация о пользователе")
@Getter
@Setter
@Accessors(chain = true)
public class UserRoleFeatures implements RoleFeatures {

    @Schema(description = "Роль")
    private UserRole role;

    @Schema(description = "Имя")
    private String firstName;

    @Schema(description = "Фамилия")
    private String lastName;
}
