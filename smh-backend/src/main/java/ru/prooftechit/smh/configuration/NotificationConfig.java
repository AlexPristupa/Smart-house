package ru.prooftechit.smh.configuration;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.prooftechit.smh.api.notification.NotificationChannel;
import ru.prooftechit.smh.api.notification.NotificationChannelType;

/**
 * @author Roman Zdoronok
 */
@Configuration
public class NotificationConfig {

    @Bean
    public Map<NotificationChannelType, NotificationChannel> getNotificationChannels(Collection<NotificationChannel> notificationChannelsList) {
        return notificationChannelsList.stream()
                                       .collect(Collectors.toMap(NotificationChannel::getNotificationChannelType,
                                                                 Function.identity()));
    }
}
