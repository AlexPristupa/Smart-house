package ru.prooftechit.smh.api.dto.documents;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.api.dto.FileDto;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Запись файловой системы. Может быть файлом или папкой, конкретный тип указан в соответствующем поле")
@Data
@Accessors(chain = true)
public class DocumentNodeDto {
    @Schema(description = "Идентификатор файла/папки")
    private Long id;
    @Schema(description = "Название файла/папки")
    private String name;
    @Schema(description = "Тип записи: файл/папка")
    private NodeType nodeType;
    @Schema(description = "Содержимое файла")
    private FileDto file;
}
