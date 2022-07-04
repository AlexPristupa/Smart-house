package ru.prooftechit.smh.api.event;

import java.time.Instant;
import lombok.Getter;
import ru.prooftechit.smh.configuration.security.SecurityContextHolderWrapper;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
@Getter
public abstract class AbstractEvent {
    private final Instant eventDate;
    private final User author;

    protected AbstractEvent() {
        this(Instant.now());
    }

    protected AbstractEvent(Instant eventDate) {
        this.eventDate = eventDate;
        this.author = SecurityContextHolderWrapper.getCurrentUser();
    }
}
