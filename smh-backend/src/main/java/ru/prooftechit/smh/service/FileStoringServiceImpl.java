package ru.prooftechit.smh.service;

import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.api.service.FileContentStore;
import ru.prooftechit.smh.api.service.FileStoringService;
import ru.prooftechit.smh.configuration.properties.FileSystemResourceProperties;
import ru.prooftechit.smh.domain.model.File;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.repository.FileRepository;
import ru.prooftechit.smh.domain.search.FileSpecification;
import ru.prooftechit.smh.exceptions.file.ContentNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import ru.prooftechit.smh.exceptions.file.FailedToSaveFileException;

/**
 * @author Yan Yukhnovets
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FileStoringServiceImpl implements FileStoringService {

    private static final Set<String> MULTIMEDIA_PREFIXES = Set.of("image"/*, "video"*/);

    private final FileRepository fileRepository;
    private final FileContentStore fileContentStore;
    private final FileSystemResourceProperties fileSystemResourceProperties;

    @Override
    public File saveFile(File override, MultipartFile multipartFile, User user) {

        File toSave;
        if (override == null) {
            toSave = new File()
                .setName(multipartFile.getOriginalFilename())
                .setMimeType(multipartFile.getContentType());
        } else {
            toSave = override.toBuilder()
                .name(Optional.ofNullable(override.getName())
                    .orElseGet(multipartFile::getOriginalFilename))
                .mimeType(Optional.ofNullable(override.getMimeType())
                    .orElseGet(multipartFile::getContentType))
                .build();
        }
        File savedFile = fileRepository.save(toSave);
        // заполняет content-id, content-length в file
        try {
            fileContentStore.setContent(savedFile, multipartFile.getInputStream());
            savedFile.setPreview(saveMultimediaPreview(multipartFile, savedFile.getContentId(), user));
        } catch (IOException e) {
            throw new FailedToSaveFileException(e);
        }
        return savedFile;
    }

    @Override
    public File saveFile(MultipartFile multipartFile, User user) {
        return saveFile(null, multipartFile, user);
    }

    @Override
    public Optional<File> getFile(Long fileId) {
        return fileRepository.findById(fileId);
    }

    @Override
    public Optional<File> getFile(UUID contentId) {
        return fileRepository.findOneByContentId(contentId);
    }

    @Override
    public Optional<File> getFile(UUID contentId, User user) {
        return fileRepository.findOne(FileSpecification.builder().contentId(contentId).user(user).build());
    }

    @Override
    public List<File> getFiles(Set<UUID> contentIds, User user) {
        return fileRepository.findAll(FileSpecification.builder().contentIds(contentIds).user(user).build());
    }

    @Override
    public void deleteFile(Long fileId, User user) {
        File file = fileRepository.findOne(FileSpecification.builder().fileId(fileId).user(user).isDeleteMode(true).build())
            .orElseThrow(ContentNotFoundException::new);
        deleteFile(file);
    }

    @Override
    public void deleteFile(File file) {
        if(file.getPreview() != null) {
            deleteFile(file.getPreview());
        }
        fileContentStore.unsetContent(file);
        fileRepository.delete(file);
    }

    @Override
    public void deleteFile(UUID contentId) {
        fileRepository.findOneByContentId(contentId).ifPresent(this::deleteFile);
    }

    @Override
    public void deleteFiles(Collection<UUID> contentIds) {
        fileRepository.findAllByContentIdIn(contentIds).forEach(this::deleteFile);
    }

    @Override
    public File rename(File file, String newName) {
        return fileRepository.save(file.setName(newName));
    }

    private File saveMultimediaPreview(MultipartFile multipartFile, UUID uuid, User user) throws IOException {
        String[] contentType = multipartFile.getContentType().split("/");
        if (contentType.length != 2 || !MULTIMEDIA_PREFIXES.contains(contentType[0])) {
            return null;
        }
        File preview = new File()
            .setName(uuid.toString() + "_preview")
            .setMimeType(multipartFile.getContentType());
        File savedPreview = fileRepository.save(preview);

        //TODO case image, video https://jira.prooftechit.com/browse/ILC-276
        BufferedImage originalImage = ImageIO.read(multipartFile.getInputStream());
        BufferedImage previewImage = Scalr.resize(originalImage,
                Scalr.Method.AUTOMATIC,
                Scalr.Mode.AUTOMATIC,
                fileSystemResourceProperties.getFilePreviewTargetWidth(),
                fileSystemResourceProperties.getFilePreviewTargetHeight());
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(previewImage, contentType[1], os);

        fileContentStore.setContent(savedPreview, new ByteArrayInputStream(os.toByteArray()));
        return savedPreview;
    }
}
