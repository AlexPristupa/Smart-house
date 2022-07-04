package ru.prooftechit.smh.configuration.properties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.convert.DurationUnit;
import ru.prooftechit.smh.notification.model.HardwareExpiredNotification;

/**
 * @author Roman Zdoronok
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "smh.scheduling", ignoreUnknownFields = false)
public class SchedulingProperties {

    @NestedConfigurationProperty
    private ServiceWorksNotification serviceWorksNotification = new ServiceWorksNotification();

    @NestedConfigurationProperty
    private HardwareNotification hardwareNotification = new HardwareNotification();

    @Getter
    @Setter
    public static class ServiceWorksNotification {
        private boolean notifyBeforeStart = true;
        private boolean notifyIfStartMissed = true;

        @DurationUnit(ChronoUnit.DAYS)
        private Duration beforeStartDuration = Duration.ofDays(1);
    }

    @Getter
    @Setter
    public static class HardwareNotification {
        @DurationUnit(ChronoUnit.DAYS)
        private Duration beforeStartDuration = Duration.ofDays(30);
    }
}
