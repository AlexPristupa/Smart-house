package ru.prooftechit.smh.controller.v1;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftechit.smh.api.service.ContentService;
import ru.prooftechit.smh.api.v1.ContentApi;

/**
 * Контроллер сессий пользователя.
 *
 * @author Andrey Kovalenko
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class ContentController extends AbstractController implements ContentApi {

    private final ContentService contentService;

    @Override
    public ResponseEntity<Resource> getContent(UUID contentId) {
        return contentService.getContent(contentId, getCurrentUser());
    }

    @Override
    public ResponseEntity<Resource> getContentPreview(UUID contentId) {
        return contentService.getContentPreview(contentId, getCurrentUser());
    }

}
