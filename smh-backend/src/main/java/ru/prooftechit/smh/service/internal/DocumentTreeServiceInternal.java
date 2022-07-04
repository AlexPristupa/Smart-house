package ru.prooftechit.smh.service.internal;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.api.dto.documents.DocumentNodeDto;
import ru.prooftechit.smh.api.dto.documents.FSPage;
import ru.prooftechit.smh.domain.model.Facility;
import ru.prooftechit.smh.domain.model.FileSystemNode;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
public interface DocumentTreeServiceInternal {
    FSPage<DocumentNodeDto> saveRootDocuments(Facility facility, List<MultipartFile> files, User user);
    FSPage<DocumentNodeDto> saveDocuments(Facility facility, FileSystemNode folderNode, List<MultipartFile> files, User user);
    int deleteNode(FileSystemNode node);
    int deleteFacilityNodes(Facility facility);
}
