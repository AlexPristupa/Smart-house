package ru.prooftechit.smh.notification.model;

import ru.prooftechit.smh.api.dto.notification.ServiceWorkNotificationDto;
import ru.prooftechit.smh.api.event.CommonServerSideEvent;
import ru.prooftechit.smh.api.notification.AbstractNotification;
import ru.prooftechit.smh.api.notification.NotificationChannelType;
import ru.prooftechit.smh.api.notification.NotificationType;
import ru.prooftechit.smh.api.notification.UsingChannels;
import ru.prooftechit.smh.notification.specification.GlobalVisibility;

import java.time.Instant;

/**
 * @author Roman Zdoronok
 */
@UsingChannels({NotificationChannelType.WEBSOCKET, NotificationChannelType.EMAIL})
public class ServiceWorkBeforeStartNotification extends AbstractNotification<ServiceWorkNotificationDto> {

    public ServiceWorkBeforeStartNotification(Instant eventDate, ServiceWorkNotificationDto payload) {
        super(new CommonServerSideEvent(eventDate),
                NotificationType.SERVICE_WORK_PLANNED,
                new GlobalVisibility(),
                payload
        );
    }
}
