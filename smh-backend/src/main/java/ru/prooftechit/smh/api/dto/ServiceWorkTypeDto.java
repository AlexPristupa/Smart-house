package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Информация о типе сервисной работе")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class ServiceWorkTypeDto extends NamedWithDescriptionDto {

    @Schema(description = "Название иконки типа сервисной работы")
    private String icon;

    @Schema(description = "Закрепленный тип сервисной работы")
    private boolean pinned;

    @Parameter(description = "Тип привязан к сервисной работе")
    private boolean linked;
}
