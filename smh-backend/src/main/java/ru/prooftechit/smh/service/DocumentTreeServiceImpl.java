package ru.prooftechit.smh.service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.api.dto.documents.DocumentNodeDto;
import ru.prooftechit.smh.api.dto.documents.DocumentNodeWithParentsDto;
import ru.prooftechit.smh.api.dto.documents.FSPage;
import ru.prooftechit.smh.api.dto.documents.FSPageImpl;
import ru.prooftechit.smh.api.dto.documents.NodeType;
import ru.prooftechit.smh.api.service.DocumentTreeService;
import ru.prooftechit.smh.api.service.FileStoringService;
import ru.prooftechit.smh.domain.model.Facility;
import ru.prooftechit.smh.domain.model.File;
import ru.prooftechit.smh.domain.model.FileSystemNode;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.repository.FileSystemNodeRepository;
import ru.prooftechit.smh.domain.util.PagedRepositoryReader;
import ru.prooftechit.smh.exceptions.InvalidOperationException;
import ru.prooftechit.smh.exceptions.RecordNotFoundException;
import ru.prooftechit.smh.mapper.DocumentTreeMapper;
import ru.prooftechit.smh.service.internal.AccessRightsService;
import ru.prooftechit.smh.service.internal.DocumentTreeServiceInternal;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Roman Zdoronok
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class DocumentTreeServiceImpl implements DocumentTreeService, DocumentTreeServiceInternal {

    private final DocumentTreeMapper documentTreeMapper;
    private final AccessRightsService accessRightsService;
    private final FileStoringService fileStoringService;
    private final FileSystemNodeRepository fileSystemNodeRepository;

    @Override
    public FSPage<DocumentNodeDto> saveRootDocuments(Facility facility, List<MultipartFile> files, User user) {
        return saveDocuments(facility, null, files, user);
    }

    @Override
    public FSPage<DocumentNodeDto> saveDocuments(Facility facility,
                                                 FileSystemNode folderNode,
                                                 List<MultipartFile> files,
                                                 User user) {
        if (folderNode != null && !NodeType.FOLDER.equals(folderNode.getNodeType())) {
            throw new InvalidOperationException(ErrorMessages.INVALID_OPERATION_NESTED_FILE);
        }

        Predicate<String> uniquenessChecker =
            folderNode == null
            ? string -> fileSystemNodeRepository.existsByFacilityAndParentIsNullAndName(facility, string)
            : string -> fileSystemNodeRepository.existsByParentAndName(folderNode, string);

        Set<String> names = new HashSet<>();
        files.stream()
             .filter(Objects::nonNull)
             .forEach(multipartFile -> {
                 String filename = multipartFile.getOriginalFilename();
                 if (names.contains(filename)
                     || uniquenessChecker.test(multipartFile.getOriginalFilename())) {
                     throw new InvalidOperationException(ErrorMessages.INVALID_OPERATION_FILE_NAME_NOT_UNIQUE);
                 }
                 names.add(filename);
             });

        List<DocumentNodeDto> nodes = files.stream()
                                           .filter(Objects::nonNull)
                                           .map(multipartFile -> {
                                               File file = fileStoringService.saveFile(multipartFile, user);
                                               FileSystemNode newDocumentNode = new FileSystemNode()
                                                   .setName(file.getName())
                                                   .setFacility(facility)
                                                   .setParent(folderNode)
                                                   .setFile(file)
                                                   .setNodeType(NodeType.FILE);
                                               return fileSystemNodeRepository.save(newDocumentNode);
                                           })
                                           .map(documentTreeMapper::toDto)
                                           .toList();
        return new FSPageImpl<>(documentTreeMapper.toDtoWithParents(folderNode), nodes);
    }

    @Override
    public int deleteNode(FileSystemNode node) {
        AtomicInteger deletedCount = new AtomicInteger();

        switch (node.getNodeType()) {
            case FOLDER -> {
                log.debug("Удаляется папка [{}]{}...", node.getId(), node.getName());
                PagedRepositoryReader<FileSystemNode> reader =
                    new PagedRepositoryReader<>(pageable -> fileSystemNodeRepository.findAllByParent(node, pageable), true);
                reader.read(nodesPage -> {
                    nodesPage.forEach(nestedNode -> deletedCount.addAndGet(deleteNode(nestedNode)));
                    return false;
                });
                fileSystemNodeRepository.delete(node);
            }
            case FILE -> {
                log.debug("Удаляется файл [{}]{}...", node.getId(), node.getName());
                File file = node.getFile();
                fileSystemNodeRepository.delete(node);
                if (file != null) {
                    fileStoringService.deleteFile(file);
                }
            }
        }
        return deletedCount.incrementAndGet();
    }

    @Override
    public int deleteFacilityNodes(Facility facility) {
        PagedRepositoryReader<FileSystemNode> reader = new PagedRepositoryReader<>(
            pageable -> fileSystemNodeRepository.findAllByFacilityAndParentIsNull(facility, pageable), true);
        AtomicInteger deletedCount = new AtomicInteger();
        reader.read(documents -> {
            log.debug("Удаляются файлы/папки объекта {} ({} из {})...",
                      facility.getId(),
                      documents.getNumberOfElements(),
                      documents.getTotalElements());
            documents.forEach(nestedNode -> deletedCount.addAndGet(deleteNode(nestedNode)));
            return false;
        });
        log.debug("Файлы/папки объекта {} удалены ({}).", facility.getId(), deletedCount.get());
        return deletedCount.get();
    }
    
    @Override
    public FSPage<DocumentNodeDto> saveRootDocuments(long facilityId,
                                                     String folder,
                                                     List<MultipartFile> files,
                                                     User user) {
        Facility facility = accessRightsService.getFacility(facilityId, user);

        FileSystemNode folderNode = fileSystemNodeRepository
            .findOneByFacilityAndParentIsNullAndName(facility, folder)
            .map(fileSystemNode -> {
                if (!NodeType.FOLDER.equals(fileSystemNode.getNodeType()) || files.isEmpty()) {
                    throw new InvalidOperationException(ErrorMessages.INVALID_OPERATION_FOLDER_NAME_NOT_UNIQUE);
                }
                return fileSystemNode;
            })
            .orElseGet(() -> {
                FileSystemNode newFolderNode = new FileSystemNode()
                    .setName(folder)
                    .setFacility(facility)
                    .setNodeType(NodeType.FOLDER);
                return fileSystemNodeRepository.save(newFolderNode);
            });

        return saveDocuments(facility, folderNode, files, user);
    }

    @Override
    public FSPage<DocumentNodeDto> saveRootDocuments(long facilityId, List<MultipartFile> files, User user) {
        Facility facility = accessRightsService.getFacility(facilityId, user);
        return saveRootDocuments(facility, files, user);
    }

    @Override
    public FSPage<DocumentNodeDto> saveDocuments(long nodeId, String folder, List<MultipartFile> files, User user) {
        FileSystemNode parentNode = accessRightsService.getFileSystemNode(nodeId, user);
        FileSystemNode folderNode = fileSystemNodeRepository
            .findOneByParentAndName(parentNode, folder)
            .map(fileSystemNode -> {
                if (!NodeType.FOLDER.equals(fileSystemNode.getNodeType()) || files.isEmpty()) {
                    throw new InvalidOperationException(ErrorMessages.INVALID_OPERATION_FOLDER_NAME_NOT_UNIQUE);
                }
                return fileSystemNode;
            })
            .orElseGet(() -> {
                FileSystemNode newFolderNode = new FileSystemNode()
                    .setName(folder)
                    .setFacility(parentNode.getFacility())
                    .setParent(parentNode)
                    .setNodeType(NodeType.FOLDER);
                return fileSystemNodeRepository.save(newFolderNode);
            });

        return saveDocuments(parentNode.getFacility(), folderNode, files, user);
    }

    @Override
    public FSPage<DocumentNodeDto> saveDocuments(long nodeId, List<MultipartFile> files, User user) {
        FileSystemNode node = accessRightsService.getFileSystemNode(nodeId, user);
        return saveDocuments(node.getFacility(), node, files, user);
    }

    @Override
    public DocumentNodeDto getDocument(long nodeId, User user) {
        FileSystemNode node = accessRightsService.getFileSystemNode(nodeId, user);
        return documentTreeMapper.toDto(node);
    }

    @Override
    public DocumentNodeDto renameDocument(long nodeId, String newName, User user) {
        FileSystemNode node = accessRightsService.getFileSystemNode(nodeId, user);
        if (node.getName().equals(newName)) {
            return documentTreeMapper.toDto(node);
        }
        Function<String, Optional<FileSystemNode>> uniquenessChecker =
            node.getParent() == null
            ? string -> fileSystemNodeRepository.findOneByFacilityAndParentIsNullAndName(node.getFacility(), string)
            : string -> fileSystemNodeRepository.findOneByParentAndName(node, string);

        Optional<FileSystemNode> existingNode = uniquenessChecker.apply(newName);
        boolean isFile = NodeType.FILE.equals(node.getNodeType());
        if (existingNode.isPresent()) {
            throw new InvalidOperationException(isFile
                                                ? ErrorMessages.INVALID_OPERATION_FILE_NAME_NOT_UNIQUE
                                                : ErrorMessages.INVALID_OPERATION_FOLDER_NAME_NOT_UNIQUE);
        }
        node.setName(newName);
        if (isFile) {
            fileStoringService.rename(node.getFile(), newName);
        }
        return documentTreeMapper.toDto(fileSystemNodeRepository.save(node));
    }

    @Override
    public void deleteDocument(long nodeId, User user) {
        FileSystemNode node = accessRightsService.getFileSystemNode(nodeId, user);
        deleteNode(node);
    }

    @Override
    public FSPage<DocumentNodeDto> getRootDocuments(long facilityId, User user, Pageable pageable) {
        Facility facility = accessRightsService.getFacility(facilityId, user);
        return new FSPageImpl<>(null, fileSystemNodeRepository.findAllByFacilityAndParentIsNull(facility, pageable)
                                                              .map(documentTreeMapper::toDto));
    }

    @Override
    public FSPage<DocumentNodeDto> getDocuments(long nodeId, User user, Pageable pageable) {
        FileSystemNode node = accessRightsService.getFileSystemNode(nodeId, user);
        if (!NodeType.FOLDER.equals(node.getNodeType())) {
            throw new InvalidOperationException(ErrorMessages.INVALID_OPERATION_NOT_A_FOLDER);
        }
        return new FSPageImpl<>(documentTreeMapper.toDtoWithParents(node),
                                fileSystemNodeRepository.findAllByParent(node, pageable)
                                                        .map(documentTreeMapper::toDto));
    }
}
