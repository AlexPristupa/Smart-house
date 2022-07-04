package ru.prooftechit.smh.api.email;


import ru.prooftechit.smh.api.notification.AbstractNotification;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
public interface EmailTemplate<N extends AbstractNotification<?>> {

    Class<N> getNotificationClass();
    Email process(N notification, User target);

}
