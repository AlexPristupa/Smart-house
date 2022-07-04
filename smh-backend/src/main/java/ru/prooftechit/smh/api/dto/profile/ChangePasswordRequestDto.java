package ru.prooftechit.smh.api.dto.profile;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import ru.prooftechit.smh.api.dto.PasswordHolder;
import ru.prooftechit.smh.constants.Patterns;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Roman Zdoronok
 */
@Data
public class ChangePasswordRequestDto implements PasswordHolder {

    public static final String OLD_PASSWORD_FIELD = "oldPassword";

    @Schema(description = "Новый пароль")
    @Pattern(regexp = Patterns.PASSWORD_PATTERN, message = ErrorMessages.PASSWORD_INVALID_FORMAT)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private String password;

    @Schema(description = "Повторить новый пароль")
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private String passwordRepeat;

    @Schema(description = "Старый пароль")
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private String oldPassword;

}
