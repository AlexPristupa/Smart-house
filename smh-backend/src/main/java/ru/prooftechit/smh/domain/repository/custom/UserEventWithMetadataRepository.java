package ru.prooftechit.smh.domain.repository.custom;

import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;
import ru.prooftechit.smh.api.dto.PageWithUnread;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.UserEvent;
import ru.prooftechit.smh.domain.search.with_metadata.UserEventMetadataSpecification;
import ru.prooftechit.smh.domain.tuple.UserEventWithMetadata;

@Repository
public interface UserEventWithMetadataRepository {
    Optional<UserEventWithMetadata> findOneWithMetadata(UserEventMetadataSpecification specification);
    PageWithUnread<UserEventWithMetadata> findAllWithMetadata(UserEventMetadataSpecification specification, Pageable pageable);
    long countUnread(Specification<UserEvent> specification, User user);
}
