package ru.prooftechit.smh.api.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.api.enums.UserEventType;

@Schema(description = "Информация о важном событии")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserEventDto extends BaseDto {
    @Schema(description = "Тип события")
    private UserEventType type;

    @ApiModelProperty(example = "false", value = "Прочитано")
    private boolean read;

    @Schema(description = "Модель")
    @JsonRawValue
    private String payload;

    @Schema(description = "Идентификатор объекта")
    private Long facilityId;
}
