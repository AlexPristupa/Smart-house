package ru.prooftechit.smh.api.dto.documents;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.api.dto.FileDto;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Запись файловой системы. Может быть файлом или папкой, конкретный тип указан в соответствующем поле")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class DocumentNodeWithParentsDto extends DocumentNodeDto {
    @Schema(description = "Родительская папка")
    private DocumentNodeWithParentsDto parent;
}
