package ru.prooftechit.smh.api.v1;

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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
@RequestMapping(ApiPaths.DOCUMENTS)
public interface DocumentApi {

    @Operation(summary = "Создать подпапку и поместить в нее файлы", tags = {Tags.DOCUMENT, Tags.FACILITY})
    @PostMapping(path = ApiPaths.DOCUMENTS_NODE_PART, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    FSPage<DocumentNodeDto> createSubFolderWithDocuments(
        @Parameter(description = "Идентификатор папки", required = true, example = "1") @PathVariable Long nodeId,
        @Parameter(description = "Название новой папки (если необходимо)") @RequestPart Optional<String> folder,
        @Parameter(description = "Файлы для загрузки") @RequestPart Optional<List<MultipartFile>> files);
    
    @Operation(summary = "Получить документ/папку", tags = Tags.DOCUMENT)
    @GetMapping(ApiPaths.DOCUMENTS_NODE_PART)
    ResponseEntity<DocumentNodeDto> getDocumentNode(
        @Parameter(description = "Идентификатор документа", required = true, example = "1") @PathVariable Long nodeId);

    @Operation(summary = "Переименовать документ/папку", tags = Tags.DOCUMENT)
    @PatchMapping(path = ApiPaths.DOCUMENTS_NODE_PART, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<?> renameDocument(
        @Parameter(description = "Идентификатор документа/папки", required = true, example = "1") @PathVariable Long nodeId,
        @Parameter(description = "Новое название документа/папки") @RequestPart String newName);

    @Operation(summary = "Удалить документ/папку", tags = Tags.DOCUMENT)
    @DeleteMapping(ApiPaths.DOCUMENTS_NODE_PART)
    ResponseEntity<?> deleteDocument(
        @Parameter(description = "Идентификатор документа/папки", required = true, example = "1") @PathVariable Long nodeId);

    @Operation(summary = "Список всех документов папки", tags = {Tags.DOCUMENT, Tags.FACILITY})
    @GetMapping (ApiPaths.DOCUMENTS_NODE_LIST_PART)
    FSPage<DocumentNodeDto> getDocuments(
        @Parameter(description = "Идентификатор папки", required = true, example = "1") @PathVariable Long nodeId,
        @PageableDefault(size = 20)
        @SortDefaults({@SortDefault(sort = FileSystemNode_.NODE_TYPE, direction = Direction.DESC),
                       @SortDefault(sort = FileSystemNode_.CREATED_AT, direction = Direction.DESC)}) Pageable pageable);

}
