package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Информация о файле")
@Data
@Accessors(chain = true)
public class FileDto {

    @Schema(example = "test.json", description = "Наименование файла")
    private String name;

    @Schema(example = "2020-09-21T12:12:12", description = "Время создания")
    private Instant createdAt;

    @Schema(example = "9ca5e12a-ac4d-4a1e-923f-cf26ce2c2f36", description = "id содержимого")
    private UUID contentId;

    @Schema(example = "1", description = "Размер файла")
    private Long contentLength;

    @Schema(example = "application/json", description = "Тип файла")
    private String mimeType;
}
