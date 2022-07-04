package ru.prooftechit.smh.controller.v1;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.api.dto.documents.DocumentNodeDto;
import ru.prooftechit.smh.api.dto.documents.FSPage;
import ru.prooftechit.smh.api.service.DocumentTreeService;
import ru.prooftechit.smh.api.v1.DocumentApi;

/**
 * @author Roman Zdoronok
 */
@RestController
@RequiredArgsConstructor
public class DocumentController extends AbstractController implements DocumentApi {

    private final DocumentTreeService documentTreeService;

    @Override
    public FSPage<DocumentNodeDto> createSubFolderWithDocuments(Long nodeId,
                                                                Optional<String> folder,
                                                                Optional<List<MultipartFile>> files) {

        return folder.map(s -> documentTreeService.saveDocuments(nodeId, s, files.orElse(Collections.emptyList()), getCurrentUser()))
                     .orElseGet(() -> documentTreeService.saveDocuments(nodeId, files.orElse(Collections.emptyList()), getCurrentUser()));
    }

    @Override
    public ResponseEntity<DocumentNodeDto> getDocumentNode(Long nodeId) {
        return ResponseEntity.ok(documentTreeService.getDocument(nodeId, getCurrentUser()));
    }

    @Override
    public ResponseEntity<DocumentNodeDto> renameDocument(Long nodeId, String newName) {
        return ResponseEntity.ok(documentTreeService.renameDocument(nodeId, newName, getCurrentUser()));
    }

    @Override
    public ResponseEntity<?> deleteDocument(Long nodeId) {
        documentTreeService.deleteDocument(nodeId, getCurrentUser());
        return ResponseEntity.noContent().build();
    }

    @Override
    public FSPage<DocumentNodeDto> getDocuments(Long nodeId, Pageable pageable) {
        return documentTreeService.getDocuments(nodeId, getCurrentUser(), pageable);
    }
}
