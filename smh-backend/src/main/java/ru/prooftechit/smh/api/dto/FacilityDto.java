package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Информация об объекте")
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class FacilityDto extends NamedWithDescriptionDto {
    @Schema(description = "Адрес")
    private String address;

    @Schema(description = "Фото обложки")
    private FileDto photo;

    @Schema(description = "Изображения")
    private List<FileDto> images;
}
