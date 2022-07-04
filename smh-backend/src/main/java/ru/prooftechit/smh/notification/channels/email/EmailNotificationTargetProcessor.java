package ru.prooftechit.smh.notification.channels.email;

import java.util.Collection;
import java.util.Collections;
import org.springframework.stereotype.Component;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.repository.UserRepository;
import ru.prooftechit.smh.notification.channels.AbstractNotificationTargetProcessor;

/**
 * Обработчик адресатов email уведомлений.
 *
 * @author Roman Zdoronok
 */
@Component
public class EmailNotificationTargetProcessor extends AbstractNotificationTargetProcessor<String> {

    public EmailNotificationTargetProcessor(UserRepository userRepository) {
        super(userRepository);
    }

    @Override
    protected Collection<String> mapUserToTargets(User user) {
        return Collections.singleton(user.getEmail());
    }

}
