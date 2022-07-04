package ru.prooftechit.smh.api.dto.registration;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.prooftechit.smh.api.dto.PasswordHolder;
import ru.prooftechit.smh.api.dto.user.UserRequestDto;
import ru.prooftechit.smh.constants.Patterns;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Учетные данные для регистрации пользователя")
@Data
public class UserRegistrationRequest implements PasswordHolder {

    @Schema(description = "Имя", example = "Иван")
    @Pattern(regexp = Patterns.FIO_PATTERN, message = ErrorMessages.NOT_ONLY_LETTERS)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private String firstName;

    @Schema(description = "Фамилия", example = "Иванов")
    @Pattern(regexp = Patterns.FIO_PATTERN, message = ErrorMessages.NOT_ONLY_LETTERS)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private String lastName;

    @Schema(description = "Отчество", example = "Иванович")
    @Pattern(regexp = Patterns.PATRONYMIC_PATTERN, message = ErrorMessages.NOT_ONLY_LETTERS)
    private String patronymic;

    @Schema(description = "Электронная почта")
    @Email(message = ErrorMessages.EMAIL_NOT_VALID)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private String email;

    @Schema(example = "1606374400000", description = "Дата рождения")
    private Instant birthDate;

    @Schema(description = "Пароль", required = true)
    @Pattern(regexp = Patterns.PASSWORD_PATTERN, message = ErrorMessages.PASSWORD_INVALID_FORMAT)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private String password;

    @Schema(description = "Подтверждение пароля", required = true)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private String passwordRepeat;
}
