package ru.prooftechit.smh.api.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Результат валидации запроса.
 *
 * @author Aleksandr Kosich
 */
@Schema(description = "Ошибка валидации данных / ошибка выполнения операции")
@Data
@Builder
public class ValidationErrorResponseDto {

    /**
     * Сообщение об ошибке.
     */
    @Schema(description = "Сообщение об ошибке")
    private String message;

    /**
     * Имя формы содержащей ошибку.
     */
    @Schema(description = "Название формы / класса данных, не прошедших проверку")
    private String formName;

    /**
     * Имя поля содержащего ошибку.
     */
    @Schema(description = "Название поля, не прошедшего проверку")
    private String fieldName;

    /**
     * Значение, не прошедшее проверку.
     */
    @Schema(description = "Значение поля, не прошедшего проверку")
    private String rejectedValue;

    /**
     * Полезная нагрузка.
     */
    @Schema(description = "Дополнительные данные")
    private Object payload;
}
