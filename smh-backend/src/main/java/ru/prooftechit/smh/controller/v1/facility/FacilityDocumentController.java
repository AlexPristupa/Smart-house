package ru.prooftechit.smh.controller.v1.facility;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.api.dto.documents.DocumentNodeDto;
import ru.prooftechit.smh.api.dto.documents.FSPage;
import ru.prooftechit.smh.api.service.DocumentTreeService;
import ru.prooftechit.smh.api.v1.facility.FacilityDocumentApi;
import ru.prooftechit.smh.controller.v1.AbstractController;

/**
 * @author Roman Zdoronok
 */
@RestController
@RequiredArgsConstructor
public class FacilityDocumentController extends AbstractController implements FacilityDocumentApi {

    private final DocumentTreeService documentTreeService;

    @Override
    public FSPage<DocumentNodeDto> createFolderWithDocuments(Long facilityId,
                                                             Optional<String> folder,
                                                             Optional<List<MultipartFile>> files) {
        return folder.map(s -> documentTreeService.saveRootDocuments(facilityId, s, files.orElse(Collections.emptyList()), getCurrentUser()))
                     .orElseGet(() -> documentTreeService.saveRootDocuments(facilityId, files.orElse(Collections.emptyList()), getCurrentUser()));
    }

    @Override
    public FSPage<DocumentNodeDto> getDocuments(Long facilityId, Pageable pageable) {
        return documentTreeService.getRootDocuments(facilityId, getCurrentUser(), pageable);
    }
}
