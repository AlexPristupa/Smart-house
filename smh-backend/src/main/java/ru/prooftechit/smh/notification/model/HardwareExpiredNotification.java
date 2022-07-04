package ru.prooftechit.smh.notification.model;

import ru.prooftechit.smh.api.dto.HardwareDto;
import ru.prooftechit.smh.api.event.CommonServerSideEvent;
import ru.prooftechit.smh.api.notification.AbstractNotification;
import ru.prooftechit.smh.api.notification.NotificationChannelType;
import ru.prooftechit.smh.api.notification.NotificationType;
import ru.prooftechit.smh.api.notification.UsingChannels;
import ru.prooftechit.smh.notification.specification.GlobalVisibility;

import java.time.Instant;

@UsingChannels({NotificationChannelType.WEBSOCKET, NotificationChannelType.EMAIL})
public class HardwareExpiredNotification extends AbstractNotification<HardwareDto> {

    public HardwareExpiredNotification(Instant eventDate, HardwareDto payload) {
        super(new CommonServerSideEvent(eventDate),
                NotificationType.HARDWARE_EXPIRED,
                new GlobalVisibility(),
                payload
        );
    }
}
