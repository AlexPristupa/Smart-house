package ru.prooftechit.smh.notification;

import java.util.Arrays;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.api.notification.AbstractNotification;
import ru.prooftechit.smh.api.notification.NotificationChannel;
import ru.prooftechit.smh.api.notification.NotificationChannelType;
import ru.prooftechit.smh.api.notification.NotificationManager;
import ru.prooftechit.smh.api.notification.UsingChannels;

/**
 * @author Roman Zdoronok
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationManagerImpl implements NotificationManager {

    private final Map<NotificationChannelType, NotificationChannel> notificationChannels;

    @Override
    public void push(AbstractNotification<?>... notifications) {
        for (AbstractNotification<?> notification : notifications) {

            Class<? extends AbstractNotification> notificationClass = notification.getClass();

            UsingChannels channels = notificationClass.getAnnotation(UsingChannels.class);
            if (channels == null) {
                log.warn("Получено уведомление, без указанных каналов доставки: {}", notificationClass);
                return;
            }

            Arrays.stream(channels.value())
                  .forEach(notificationChannelType -> {
                      NotificationChannel channel = notificationChannels.get(notificationChannelType);
                      if (channel == null) {
                          log.warn("Получено уведомление, которое использует неизвестный канал доставки: {} {}",
                                   notificationChannelType.name(),
                                   notificationClass);
                          return;
                      }

                      try {
                          channel.send(notification);
                      } catch (Exception e) {
                          log.error("Не удалось доставить уведомление используя " + notificationChannelType.name(), e);
                      }
                  });
        }
    }
}
