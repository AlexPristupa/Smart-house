package ru.prooftechit.smh.domain.search.with_metadata;


import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.UserEvent;
import ru.prooftechit.smh.domain.model.metadata.UserEventMetadata;
import ru.prooftechit.smh.domain.tuple.UserEventWithMetadata;

/**
 * @author Roman Zdoronok
 */
public interface UserEventMetadataSpecification
    extends WithMetadataSpecification<UserEvent, UserEventMetadata, UserEventWithMetadata> {

    User getUser();

}
