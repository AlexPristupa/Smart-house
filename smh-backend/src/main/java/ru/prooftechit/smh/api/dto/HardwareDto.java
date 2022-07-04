package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Информация об оборудовании")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class HardwareDto extends NamedWithDescriptionDto {

    @Schema(description = "Фото обложки")
    private FileDto photo;

    @Schema(description = "Изображения")
    private List<FileDto> images;

    @Schema(description = "Модель")
    private String model;

    @Schema(description = "Серийный номер")
    private String serialNumber;

    @Schema(description = "Дата установки")
    private Instant installedAt;

    @Schema(description = "Дата следующей поверки")
    private Instant expiresAt;

    @Schema(description = "Установщик")
    private String installer;

}
