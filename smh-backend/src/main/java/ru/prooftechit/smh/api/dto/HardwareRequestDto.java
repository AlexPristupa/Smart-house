package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.http.MediaType;
import ru.prooftechit.smh.validation.ErrorMessages;
import ru.prooftechit.smh.validation.constraint.ContentId;
import ru.prooftechit.smh.validation.constraint.ContentType;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Информация об оборудовании, для его создания")
@Data
@Accessors(chain = true)
public class HardwareRequestDto {

    @Schema(description = "Название", required = true)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private final String name;

    @Schema(description = "Описание")
    private final String description;

    @Schema(description = "Модель", required = true)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private final String model;

    @Schema(description = "Серийный номер", required = true)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private final String serialNumber;

    @Schema(description = "Дата установки", required = true)
    @NotNull(message = ErrorMessages.EMPTY_FIELD)
    private final Instant installedAt;

    @Schema(description = "Дата следующей поверки", required = true)
    @NotNull(message = ErrorMessages.EMPTY_FIELD)
    private final Instant expiresAt;

    @Schema(description = "Установщик", required = true)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private final String installer;

    @Schema(description = "Идентификатор ранее загруженной фотографии")
    @ContentId
    @ContentType(mediaTypes = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
    private final UUID photo;

    @Schema(description = "Список идентификаторов ранее загруженных изображений")
    @Size(max = 10, message = ErrorMessages.FILES_COUNT_MAX_10)
    @Valid //данная аннотация необходима для валидации элементов коллекции
    private final Set<
        @ContentId
        @ContentType(mediaTypes = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
            UUID> images;
}
