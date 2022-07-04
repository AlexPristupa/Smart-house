package ru.prooftechit.smh.api.notification;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Roman Zdoronok
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsingChannels {
    NotificationChannelType[] value() default {NotificationChannelType.EMAIL};
}
