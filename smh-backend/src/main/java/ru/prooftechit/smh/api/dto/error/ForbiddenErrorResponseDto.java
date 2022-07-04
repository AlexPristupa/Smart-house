package ru.prooftechit.smh.api.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Сообщение об ошибке, при органиченном доступе к ресурсу")
@Data
@Builder
public class ForbiddenErrorResponseDto {
    /**
     * Сообщение об ошибке.
     */
    @Schema(description = "Сообщение об ошибке")
    private String message;

    /**
     * Причина отказа в доступе.
     */
    @Schema(description = "Причина отказа в доступе к ресурсу")
    private AccessDeniedReason reason;

    /**
     * Полезная нагрузка.
     */
    @Schema(description = "Дополнительные данные")
    private Object payload;
}
