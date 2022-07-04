package ru.prooftechit.smh.api.notification;

import java.time.Instant;
import lombok.Builder;
import lombok.Getter;

/**
 * @author Roman Zdoronok
 */
@Getter
@Builder
public class NotificationPayloadWrapper<P> {
    private final NotificationType type;
    private final Instant eventDate;
    private final P payload;
}
