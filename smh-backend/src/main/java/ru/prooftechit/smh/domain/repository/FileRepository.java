package ru.prooftechit.smh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.prooftechit.smh.domain.model.File;

import java.util.*;

/**
 * Репозиторий сущности "Файл".
 *
 * @author Yan Yukhnovets
 */
@Repository
public interface FileRepository extends JpaRepository<File, Long>, JpaSpecificationExecutor<File> {

    Optional<File> findOneByContentId(UUID contentId);

    List<File> findAllByContentIdIn(Collection<UUID> uuids);

    boolean existsByContentId(UUID contentId);

    @Query("select case "
        + "when not exists (select 1 from File f where f.contentId = :contentId) "
        + "or count(f) > 0 "
        + "then true "
        + "else false end "
        + "from File f where f.contentId = :contentId and f.mimeType in (:mimeTypes)")
    boolean existsByContentIdAndMimeTypeIn(UUID contentId, Collection<String> mimeTypes);

    @Modifying
    @Query("delete from File f where f.contentId in :ids")
    void deleteByContentId(Set<UUID> ids);

    @Query("select file from File file join File file2 on file2.preview = file where file2.contentId = :contentId")
    Optional<File> findPreviewByContentId(UUID contentId);
}
