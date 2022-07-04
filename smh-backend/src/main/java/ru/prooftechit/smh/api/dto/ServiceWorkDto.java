package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.api.enums.ServiceWorkResolution;
import ru.prooftechit.smh.api.enums.ServiceWorkStatus;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Информация о сервисной работе")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ServiceWorkDto extends NamedWithDescriptionDto {

    @Schema(description = "Тип сервисной работы")
    private ServiceWorkTypeDto type;

    @Schema(description = "Статус сервисной работы")
    private ServiceWorkStatus status;

    @Schema(description = "Состояние сервисной работы")
    private ServiceWorkResolution resolution;

    @Schema(description = "Дата и время начала работы")
    private Instant startTime;

    @Schema(description = "Дата и время завершения работы")
    private Instant finishTime;

    @Schema(description = "Дата и время подтверждения состояния работы")
    private Instant resolutionTime;

}
