package ru.prooftechit.smh.api.notification;

/**
 * @author Roman Zdoronok
 */
public interface NotificationChannel {

    NotificationChannelType getNotificationChannelType();

    void send(AbstractNotification<?> notification);

}
