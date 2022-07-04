package ru.prooftechit.smh.notification.channels.email;

import java.util.Collection;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.api.email.Email;
import ru.prooftechit.smh.api.email.EmailTemplate;
import ru.prooftechit.smh.api.notification.AbstractNotification;
import ru.prooftechit.smh.api.notification.NotificationChannelType;
import ru.prooftechit.smh.configuration.mail.TemplateAggregator;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.notification.channels.AbstractNotificationChannel;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@Service
public class EmailNotificationChannel extends AbstractNotificationChannel<String> {

    private final EmailMessageSender emailMessageSender;
    private final TemplateAggregator templateAggregator;

    public EmailNotificationChannel(EmailMessageSender emailMessageSender,
                                    TemplateAggregator templateAggregator,
                                    EmailNotificationTargetProcessor emailNotificationTargetProcessor) {
        super(emailNotificationTargetProcessor);
        this.emailMessageSender = emailMessageSender;
        this.templateAggregator = templateAggregator;
    }

    @Override
    public NotificationChannelType getNotificationChannelType() {
        return NotificationChannelType.EMAIL;
    }

    @Override
    public void send(AbstractNotification<?> notification, Map<User, Collection<String>> targets) {

        if(targets == null || targets.isEmpty()) {
            log.error("Не найдено ни одного адресата для почтовой рассылки: {}", notification.getClass().getName());
            return;
        }

        EmailTemplate<AbstractNotification<?>> emailTemplate = templateAggregator.get(notification.getClass());

        if(emailTemplate == null) {
            log.error("Нет шаблона письма для уведомления {}", notification.getClass().getName());
            return;
        }

        targets.forEach((user, emailAddresses) -> {
            Email email = emailTemplate.process(notification, user);
            //TODO: implement batch sending
            for (String emailAddress : emailAddresses) {
                emailMessageSender.send(email, emailAddress);
            }
        });

    }

}
