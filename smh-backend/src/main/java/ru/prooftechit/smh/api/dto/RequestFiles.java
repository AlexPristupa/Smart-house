package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import javax.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.validation.constraint.ImageMultipartFile;
import ru.prooftechit.smh.validation.constraint.NotEmptyMultipartFile;

/**
 * @author Roman Zdoronok
 */
@Getter
@Setter
public class RequestFiles {

    @Schema(description = "Фото объекта")
    @NotEmptyMultipartFile
    @ImageMultipartFile
    private MultipartFile photo;

    @Schema(description = "Дополнительные изображения")
    @Valid
    private List<@NotEmptyMultipartFile @ImageMultipartFile MultipartFile> images;
}
