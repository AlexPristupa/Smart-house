package ru.prooftechit.smh.notification.specification;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import ru.prooftechit.smh.api.notification.AbstractNotification;
import ru.prooftechit.smh.domain.model.User;

/**
 * Обработчик адресатов уведомлений.
 * Предназначен для поиска/фильтрации адресатов и передачи их координат в канал отправки уведомлений.
 *
 * @author Roman Zdoronok
 */
public interface NotificationTargetsProcessor<T> {

    void processTargets(AbstractNotification<?> notification, Consumer<Map<User, Collection<T>>> targetsConsumer);

}
