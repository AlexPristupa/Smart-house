package ru.prooftechit.smh.configuration.mail;

import java.util.HashMap;
import java.util.Map;
import ru.prooftechit.smh.api.email.EmailTemplate;
import ru.prooftechit.smh.api.notification.AbstractNotification;

/**
 * @author Roman Zdoronok
 */
public class TemplateAggregator {
    private final Map<Class<? extends AbstractNotification<?>>, EmailTemplate<? extends AbstractNotification<?>>> templateContextProviders = new HashMap<>();

    protected void put(EmailTemplate<? extends AbstractNotification<?>> template) {
        templateContextProviders.put(template.getNotificationClass(), template);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public EmailTemplate<AbstractNotification<?>> get(Class<? extends AbstractNotification> clazz) {
        return (EmailTemplate<AbstractNotification<?>>) templateContextProviders.get(clazz);
    } 

}
