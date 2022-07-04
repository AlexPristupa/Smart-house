package ru.prooftechit.smh.api.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import ru.prooftechit.smh.api.dto.FileDto;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.constants.Patterns;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * Учетные данные пользователя.
 *
 * @author Roman Zdoronok
 */
@Schema(description = "Учетные данные пользователя")
@Data
public class UserDto {
    @Schema(description = "Идентификатор пользователя", example = "123")
    private Long id;

    @Schema(description = "Имя", example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    @Schema(description = "Отчество", example = "Иванович")
    private String patronymic;

    @Schema(description = "Электронная почта")
    private String email;

    @Schema(example = "1606374400000", description = "Дата рождения")
    private Instant birthDate;

    @Schema(description = "Роль пользователя")
    private UserRole role;

    @Schema(description = "Фото профиля")
    private FileDto photo;
}
