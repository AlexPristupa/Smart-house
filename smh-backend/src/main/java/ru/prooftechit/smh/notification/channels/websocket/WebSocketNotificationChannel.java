package ru.prooftechit.smh.notification.channels.websocket;

import java.util.Collection;
import java.util.Map;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.api.notification.AbstractNotification;
import ru.prooftechit.smh.api.notification.NotificationChannelType;
import ru.prooftechit.smh.api.notification.NotificationPayloadWrapper;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.notification.channels.AbstractNotificationChannel;

/**
 * @author Roman Zdoronok
 */
@Service
public class WebSocketNotificationChannel extends AbstractNotificationChannel<WebSocketTarget> {

    
    private final SimpMessagingTemplate messagingTemplate;

    public WebSocketNotificationChannel(SimpMessagingTemplate messagingTemplate,
                                        WebSocketNotificationTargetProcessor webSocketNotificationTargetProcessor) {
        super(webSocketNotificationTargetProcessor);
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public NotificationChannelType getNotificationChannelType() {
        return NotificationChannelType.WEBSOCKET;
    }

    @Override
    protected void send(AbstractNotification<?> notification, Map<User, Collection<WebSocketTarget>> targets) {
        NotificationPayloadWrapper<?> payload = notification.wrap();
        targets.forEach((user, webSocketTargets) -> {
            for (WebSocketTarget target : webSocketTargets) {
                messagingTemplate.convertAndSendToUser(target.getUser(), target.getQueue(), payload);
            }
        });

    }
}
