package ru.prooftechit.smh.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.api.service.ContentService;
import ru.prooftechit.smh.api.service.FileContentStore;
import ru.prooftechit.smh.domain.model.File;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.repository.FileRepository;
import ru.prooftechit.smh.domain.search.FileSpecification;
import ru.prooftechit.smh.exceptions.file.ContentNotFoundException;
import ru.prooftechit.smh.exceptions.file.FileGoneException;

@Service
@RequiredArgsConstructor
public class ContentServiceImpl implements ContentService {

    private final FileRepository fileRepository;
    private final FileContentStore fileContentStore;

    @Override
    public ResponseEntity<Resource> getFileResponse(File file) {
        Resource resource = fileContentStore.getResource(file);
        if(resource == null) {
            throw new FileGoneException();
        }

        return ResponseEntity.ok()
                             .headers(headers -> {
                                 headers.setContentDisposition(ContentDisposition.builder("inline")
                                                                                 .filename(file.getName())
                                                                                 .build());
                                 headers.setContentType(MediaType.parseMediaType(file.getMimeType()));
                                 headers.setContentLength(file.getContentLength());
                             })
                             .body(resource);
    }

    @Override
    public ResponseEntity<Resource> getContent(UUID contentId, User user) {
        File file = fileRepository.findOne(FileSpecification.builder().contentId(contentId).user(user).build())
            .orElseThrow(ContentNotFoundException::new);
        return getFileResponse(file);
    }

    @Override
    public ResponseEntity<Resource> getContentPreview(UUID contentId, User user) {
        File preview = fileRepository.findOne(FileSpecification.builder().contentId(contentId).user(user).isPreview(true).build())
            .orElseThrow(ContentNotFoundException::new);
        return getFileResponse(preview);
    }
}
