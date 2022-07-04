package ru.prooftechit.smh.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.prooftechit.smh.configuration.swagger.Tags;
import ru.prooftechit.smh.constants.ApiPaths;

/**
 * @author Andrey Kovalenko
 */
@Tag(name = Tags.CONTENT, description = "Загрузка файлов и аудио/видео потоков")
@RequestMapping(ApiPaths.CONTENT)
public interface ContentApi {

    @Operation(summary = "Загрузка контента", tags = Tags.CONTENT)
    @GetMapping(ApiPaths.CONTENT_ID_PART)
    ResponseEntity<Resource> getContent(
        @Parameter(description = "Идентификатор контента", required = true, example = "16ad094c-d676-4eb1-ada2-3e1a446d11b0") @PathVariable UUID contentId);

    @Operation(summary = "Загрузка превью контента", tags = Tags.CONTENT)
    @GetMapping(ApiPaths.CONTENT_ID_PREVIEW_PART)
    ResponseEntity<Resource> getContentPreview(
        @Parameter(description = "Идентификатор контента", required = true, example = "16ad094c-d676-4eb1-ada2-3e1a446d11b0") @PathVariable UUID contentId);
}
