package ru.prooftechit.smh.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.prooftechit.smh.domain.model.metadata.UserEventMetadata;
import ru.prooftechit.smh.domain.model.metadata.EntityMetadataKey;

@Repository
public interface UserEventMetadataRepository extends JpaRepository<UserEventMetadata, EntityMetadataKey>,
    JpaSpecificationExecutor<UserEventMetadata> {

}
