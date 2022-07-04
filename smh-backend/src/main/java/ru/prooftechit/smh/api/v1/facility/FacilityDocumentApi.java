package ru.prooftechit.smh.api.v1.facility;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.api.dto.documents.DocumentNodeDto;
import ru.prooftechit.smh.api.dto.documents.FSPage;
import ru.prooftechit.smh.configuration.swagger.Tags;
import ru.prooftechit.smh.constants.ApiPaths;
import ru.prooftechit.smh.domain.model.FileSystemNode_;

/**
 * @author Roman Zdoronok
 */
@Tag(name = Tags.DOCUMENT, description = "API работы с документами")
@RequestMapping(ApiPaths.FACILITY_DOCUMENTS)
public interface FacilityDocumentApi {

    @Operation(summary = "Создать новую папку / загрузить документы. Или оба варианта", tags = {Tags.DOCUMENT, Tags.FACILITY})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FSPage<DocumentNodeDto> createFolderWithDocuments(
        @Parameter(description = "Идентификатор объекта", required = true, example = "1") @PathVariable Long facilityId,
        @Parameter(description = "Название новой папки (если необходимо)") @RequestPart Optional<String> folder,
        @Parameter(description = "Файлы для загрузки") @RequestPart Optional<List<MultipartFile>> files);

    @Operation(summary = "Список всех документов/папок объекта", tags = {Tags.DOCUMENT, Tags.FACILITY})
    @GetMapping
    FSPage<DocumentNodeDto> getDocuments(
        @Parameter(description = "Идентификатор объекта", required = true, example = "1") @PathVariable Long facilityId,
        @PageableDefault(size = 20)
        @SortDefaults({@SortDefault(sort = FileSystemNode_.NODE_TYPE, direction = Direction.DESC),
                       @SortDefault(sort = FileSystemNode_.CREATED_AT, direction = Direction.DESC)}) Pageable pageable);

}
