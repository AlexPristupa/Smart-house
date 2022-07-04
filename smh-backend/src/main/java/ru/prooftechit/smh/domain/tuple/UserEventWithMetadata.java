package ru.prooftechit.smh.domain.tuple;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.prooftechit.smh.domain.model.UserEvent;
import ru.prooftechit.smh.domain.model.metadata.UserEventMetadata;

@Getter
@Setter
@NoArgsConstructor
public class UserEventWithMetadata extends EntityWithMetadata<UserEvent, UserEventMetadata> {
    public UserEventWithMetadata(UserEvent entity, UserEventMetadata metadata) {
        super(entity, metadata);
    }
}
