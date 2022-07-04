package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.api.enums.ServiceWorkResolution;
import ru.prooftechit.smh.validation.ErrorMessages;
import ru.prooftechit.smh.validation.constraint.ServiceWorkType;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Информация о сервисной работе, для ее создания")
@Data
@Accessors(chain = true)
public class ServiceWorkRequestDto {

    @Schema(description = "Тип сервисной работы", required = true)
    @NotNull(message = ErrorMessages.EMPTY_FIELD)
    @ServiceWorkType
    private final Long serviceWorkType;

    @Schema(description = "Название", required = true)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private final String name;

    @Schema(description = "Описание")
    private final String description;

    @Schema(description = "Дата и время начала работы", required = true)
    @NotNull(message = ErrorMessages.EMPTY_FIELD)
    private final Instant startTime;

    @Schema(description = "Дата и время завершения работы", required = true)
    @NotNull(message = ErrorMessages.EMPTY_FIELD)
    private final Instant finishTime;

    @Schema(description = "Резолюция работы")
    private final ServiceWorkResolution resolution;

}
