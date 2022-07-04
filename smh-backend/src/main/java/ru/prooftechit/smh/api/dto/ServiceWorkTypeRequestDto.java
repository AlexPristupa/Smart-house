package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Информация о типе сервисной работе, для его создания")
@Data
@Accessors(chain = true)
public class ServiceWorkTypeRequestDto {

    @Schema(description = "Название", required = true)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private final String name;

    @Schema(description = "Описание", required = true)
    private final String description;

    @Schema(description = "Название иконки типа сервисной работы")
    private final String icon;

    @Schema(description = "Закрепленный тип сервисной работы", defaultValue = "false")
    private final boolean pinned;
}
