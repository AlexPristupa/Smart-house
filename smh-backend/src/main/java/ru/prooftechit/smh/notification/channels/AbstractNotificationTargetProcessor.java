package ru.prooftechit.smh.notification.channels;

import java.util.Collection;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.prooftechit.smh.api.notification.AbstractNotification;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.repository.UserRepository;
import ru.prooftechit.smh.domain.util.PagedRepositoryReader;
import ru.prooftechit.smh.notification.specification.NotificationTargetsProcessor;

/**
 * Обработчик адресатов уведомлений, общая реализация.
 *
 * @author Roman Zdoronok
 */
@RequiredArgsConstructor
public abstract class AbstractNotificationTargetProcessor<T> implements NotificationTargetsProcessor<T> {

    private static final int DEFAULT_BATCH_SIZE = 50;

    private final UserRepository userRepository;

    protected abstract Collection<T> mapUserToTargets(User user);

    protected int getBatchSize() {
        return DEFAULT_BATCH_SIZE;
    }

    /**
     * Метод предназначен для дополнительной фильтрации адресатов с учетом специфики канала доставки.
     *
     * @param original оригинальная спецификация
     * @return новая спецификация с уточнением критериев поиска, либо возвращается спецификация без изменений
     */
    protected Specification<User> modifySpecification(Specification<User> original) {
        return original;
    }

    @Override
    public void processTargets(AbstractNotification<?> notification, Consumer<Map<User, Collection<T>>> targetsConsumer) {
        Specification<User> originalSpecification = notification.getSpecification();
        Specification<User> modifiedSpecification = modifySpecification(originalSpecification);
        PagedRepositoryReader<User> reader = new PagedRepositoryReader<>(
            pageable -> userRepository.findAll(modifiedSpecification, pageable),
            DEFAULT_BATCH_SIZE);

        reader.read(users -> {
            targetsConsumer.accept(users
                                       .getContent()
                                       .stream()
                                       .collect(Collectors.toMap(Function.identity(), this::mapUserToTargets)));
            return false;
        });

    }
}
