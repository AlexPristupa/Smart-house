package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Запись у которой есть название и описание")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class NamedWithDescriptionDto extends BaseDto {
    @Schema(description = "Название")
    private String name;
    @Schema(description = "Описание")
    private String description;
}
