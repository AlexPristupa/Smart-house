package ru.prooftechit.smh.api.notification;

/**
 * @author Roman Zdoronok
 */
public interface NotificationManager {
    void push(AbstractNotification<?>... notifications);
}
