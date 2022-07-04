package ru.prooftechit.smh.notification.channels;

import java.util.Collection;
import java.util.Map;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import ru.prooftechit.smh.api.notification.AbstractNotification;
import ru.prooftechit.smh.api.notification.NotificationChannel;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.notification.specification.NotificationTargetsProcessor;

/**
 * @author Roman Zdoronok
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractNotificationChannel<T> implements NotificationChannel {

    private final NotificationTargetsProcessor<T> targetsProcessor;

    protected abstract void send(AbstractNotification<?> notification, Map<User, Collection<T>> targets);

    @Override
    public void send(AbstractNotification<?> notification) {
        targetsProcessor.processTargets(notification, t -> send(notification, t));
    }
}
