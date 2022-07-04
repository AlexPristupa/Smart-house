package ru.prooftechit.smh.api.service;

import java.util.UUID;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import ru.prooftechit.smh.domain.model.File;
import ru.prooftechit.smh.domain.model.User;

/**
 * Сервис для передачи на клиент контента
 */
public interface ContentService {

    /**
     * Выдача ресурса в виде потока
     *
     * @param file запрашиваемый файл
     *
     * @return ResponseEntity для выдачи клиенту
     */
    ResponseEntity<Resource> getFileResponse(File file);


    /**
     * Выдача ресурса в виде потока
     *
     * @param contentId id ресурс
     * @param user пользователь, запрашивающий ресурс
     *
     * @return ResponseEntity для выдачи клиенту
     */
    ResponseEntity<Resource> getContent(UUID contentId, User user);

    /**
     * Выдача превью ресурса в виде потока
     *
     * @param contentId id ресурса
     * @param user пользователь, запрашивающий ресурс
     *
     * @return ResponseEntity для выдачи клиенту
     */
    ResponseEntity<Resource> getContentPreview(UUID contentId, User user);
}
