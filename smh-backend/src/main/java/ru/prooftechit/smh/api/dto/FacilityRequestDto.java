package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
@Schema(description = "Информация об объекте, для его создания")
@Data
@Accessors(chain = true)
public class FacilityRequestDto {

    @Schema(description = "Название", required = true)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private final String name;

    @Schema(description = "Описание")
    private final String description;

    @Schema(description = "Адрес", required = true)
    @NotBlank(message = ErrorMessages.EMPTY_FIELD)
    private final String address;

    @Schema(description = "Идентификатор ранее загруженной фотографии")
    @ContentId
    @ContentType(mediaTypes = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
    private final UUID photo;

    @Schema(description = "Список идентификаторов ранее загруженных изображений")
    @Size(max = 10, message = ErrorMessages.FILES_COUNT_MAX_10)
    @Valid //данная аннотация необходима для валидации элементов коллекции
    private final Set<
        @ContentId
        @ContentType(mediaTypes = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
            UUID> images;

}
