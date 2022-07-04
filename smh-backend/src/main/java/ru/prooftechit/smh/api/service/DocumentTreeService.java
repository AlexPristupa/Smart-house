package ru.prooftechit.smh.api.service;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.api.dto.documents.DocumentNodeDto;
import ru.prooftechit.smh.api.dto.documents.FSPage;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
public interface DocumentTreeService {
    FSPage<DocumentNodeDto> saveRootDocuments(long facilityId, String folder, List<MultipartFile> files, User user);
    FSPage<DocumentNodeDto> saveRootDocuments(long facilityId, List<MultipartFile> files, User user);
    FSPage<DocumentNodeDto> saveDocuments(long nodeId, String folder, List<MultipartFile> files, User user);
    FSPage<DocumentNodeDto> saveDocuments(long nodeId, List<MultipartFile> files, User user);
    DocumentNodeDto getDocument(long nodeId, User user);
    DocumentNodeDto renameDocument(long nodeId, String newName, User user);
    void deleteDocument(long nodeId, User user);
    FSPage<DocumentNodeDto> getRootDocuments(long facilityId, User user, Pageable pageable);
    FSPage<DocumentNodeDto> getDocuments(long nodeId, User user, Pageable pageable);
}
