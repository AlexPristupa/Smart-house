package ru.prooftechit.smh.domain.repository;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.prooftechit.smh.api.dto.documents.NodeType;
import ru.prooftechit.smh.domain.model.Facility;
import ru.prooftechit.smh.domain.model.File;
import ru.prooftechit.smh.domain.model.FileSystemNode;

/**
 * @author Roman Zdoronok
 */
@Repository
public interface FileSystemNodeRepository extends JpaRepository<FileSystemNode, Long>, JpaSpecificationExecutor<FileSystemNode> {

    Optional<FileSystemNode> findOneByFile(File file);
    Optional<FileSystemNode> findOneByFacilityAndParentIsNullAndName(Facility facility, String name);
    Optional<FileSystemNode> findOneByFacilityAndParentIsNullAndNameAndNodeType(Facility facility, String name, NodeType nodeType);
    Optional<FileSystemNode> findOneByParentAndName(FileSystemNode parent, String name);
    Optional<FileSystemNode> findOneByParentAndNameAndNodeType(FileSystemNode parent, String name, NodeType nodeType);

    boolean existsByFile(File file);
    boolean existsByFacilityAndParentIsNullAndName(Facility facility, String name);
    boolean existsByFacilityAndParentIsNullAndNameAndNodeType(Facility facility, String name, NodeType nodeType);
    boolean existsByParentAndName(FileSystemNode parent, String name);
    boolean existsByParentAndNameAndNodeType(FileSystemNode parent, String name, NodeType nodeType);

    Page<FileSystemNode> findAllByParent(FileSystemNode parentNode, Pageable pageable);
    Page<FileSystemNode> findAllByParentAndNodeType(FileSystemNode parentNode, NodeType nodeType, Pageable pageable);
    Page<FileSystemNode> findAllByFacilityAndParentIsNull(Facility facility, Pageable pageable);
    Page<FileSystemNode> findAllByFacilityAndParentIsNullAndNodeType(Facility facility, NodeType nodeType, Pageable pageable);

}
