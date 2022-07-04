package ru.prooftechit.smh.api.dto.profile;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.UUID;
import lombok.Data;
import org.springframework.http.MediaType;
import ru.prooftechit.smh.api.dto.FileDto;
import ru.prooftechit.smh.validation.constraint.ContentId;
import ru.prooftechit.smh.validation.constraint.ContentType;

/**
 * @author Roman Zdoronok
 */
@Data
public class ProfileRequestDto {
    @Schema(description = "Имя", example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия", example = "Иванов")
    private String lastName;

    @Schema(description = "Отчество", example = "Иванович")
    private String patronymic;

    @Schema(description = "Дата рождения")
    private Instant birthDate;

    @Schema(description = "Идентификатор ранее загруженной фотографии")
    @ContentId
    @ContentType(mediaTypes = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
    private final UUID photo;
}
