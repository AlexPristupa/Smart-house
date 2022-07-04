package ru.prooftechit.smh.notification.channels.websocket;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.user.SimpUser;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.prooftechit.smh.configuration.security.UserDetailsImpl;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.User_;
import ru.prooftechit.smh.domain.repository.UserRepository;
import ru.prooftechit.smh.notification.channels.AbstractNotificationTargetProcessor;

/**
 * Обработчик адресатов уведомлений, отправляемых по вебсокету.
 *
 * @author Roman Zdoronok
 */
@Component
public class WebSocketNotificationTargetProcessor extends AbstractNotificationTargetProcessor<WebSocketTarget> {

    private final SimpUserRegistry simpUserRegistry;

    public WebSocketNotificationTargetProcessor(UserRepository userRepository,
                                                SimpUserRegistry simpUserRegistry) {
        super(userRepository);
        this.simpUserRegistry = simpUserRegistry;
    }

    @Override
    protected Collection<WebSocketTarget> mapUserToTargets(User user) {
        return Collections.singleton(WebSocketTarget.forUser(user.getEmail()));
    }

    @Override
    protected Specification<User> modifySpecification(Specification<User> original) {
        return new ConnectedUsersSpecificationWrapper(original);
    }

    @RequiredArgsConstructor
    private class ConnectedUsersSpecificationWrapper implements Specification<User> {

        private static final long serialVersionUID = -6141909155736693280L;

        private final Specification<User> originalSpecification;

        @Override
        public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
            return builder.and(root.get(User_.ID)
                                   .in(simpUserRegistry.getUsers().stream()
                                                       .map(SimpUser::getPrincipal)
                                                       .map(Authentication.class::cast)
                                                       .map(Authentication::getPrincipal)
                                                       .map(UserDetailsImpl.class::cast)
                                                       .map(UserDetailsImpl::getUser)
                                                       .map(User::getId)
                                                       .collect(Collectors.toSet())),
                               originalSpecification.toPredicate(root, query, builder));
        }
    }
}
