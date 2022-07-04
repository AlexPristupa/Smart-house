package ru.prooftechit.smh.api.notification;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.prooftechit.smh.api.event.AbstractEvent;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class AbstractNotification<P> {

    private final AbstractEvent sourceEvent;
    private final NotificationType type;
    private final Specification<User> specification;
    private final P payload;

    public NotificationPayloadWrapper<P> wrap() {
        return NotificationPayloadWrapper.<P>builder()
                                         .type(type)
                                         .eventDate(sourceEvent.getEventDate())
                                         .payload(payload)
                                         .build();
    }
}
