package ru.prooftechit.smh.api.service;

import java.util.List;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.domain.model.File;
import ru.prooftechit.smh.domain.model.User;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Сервис на управление файлами из ОС.
 *
 * @author Yan Yukhnovets
 */
public interface FileStoringService {

    File saveFile(File override, MultipartFile multipartFile, User user);

    File saveFile(MultipartFile multipartFile, User user);

    Optional<File> getFile(Long fileId);

    Optional<File> getFile(UUID contentId);

    Optional<File> getFile(UUID contentId, User user);

    List<File> getFiles(Set<UUID> contentIds, User user);

    void deleteFile(Long fileId, User user);

    void deleteFile(UUID contentId);

    void deleteFile(File file);

    void deleteFiles(Collection<UUID> contentIds);

    File rename(File file, String newName);
}
