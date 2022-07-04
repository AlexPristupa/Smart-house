package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Общая информация о записи")
@Data
@Accessors(chain = true)
public class BaseDto {
    @Schema(description = "Идентификатор", example = "1")
    private Long id;
}
