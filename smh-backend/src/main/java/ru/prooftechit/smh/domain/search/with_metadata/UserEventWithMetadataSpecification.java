package ru.prooftechit.smh.domain.search.with_metadata;

import org.springframework.data.jpa.domain.Specification;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.UserEvent;
import ru.prooftechit.smh.domain.model.metadata.UserEventMetadata;
import ru.prooftechit.smh.domain.tuple.UserEventWithMetadata;

/**
 * @author Roman Zdoronok
 */
public class UserEventWithMetadataSpecification
    extends AbstractMetadataSpecification<UserEvent, UserEventMetadata, UserEventWithMetadata>
    implements UserEventMetadataSpecification {

    public UserEventWithMetadataSpecification(User user, Specification<UserEvent> specification) {
        super(user, specification);
    }

}
