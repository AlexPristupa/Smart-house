package ru.prooftechit.smh.notification.model;

import ru.prooftechit.smh.api.dto.notification.ServiceWorkNotificationDto;
import ru.prooftechit.smh.api.notification.AbstractNotification;
import ru.prooftechit.smh.api.notification.NotificationChannelType;
import ru.prooftechit.smh.api.notification.NotificationType;
import ru.prooftechit.smh.api.notification.UsingChannels;
import ru.prooftechit.smh.event.model.service_work.AbstractServiceWorkEvent;
import ru.prooftechit.smh.notification.specification.GlobalVisibility;

@UsingChannels({NotificationChannelType.WEBSOCKET, NotificationChannelType.EMAIL})
public class ServiceWorkFinishedNotification extends AbstractNotification<ServiceWorkNotificationDto> {

    public ServiceWorkFinishedNotification(AbstractServiceWorkEvent sourceEvent, ServiceWorkNotificationDto payload) {
        super(sourceEvent,
                NotificationType.SERVICE_WORK_FINISHED,
                new GlobalVisibility(),
                payload
        );
    }
}
