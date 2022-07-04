package ru.prooftechit.smh.notification.specification;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.User_;

/**
 * @author Roman Zdoronok
 */
@Getter
public class MultipleUsersVisibility extends AbstractNotificationUserSpecification {

    @Serial
    private static final long serialVersionUID = 539530396624726426L;

    private final Collection<User> users;

    public MultipleUsersVisibility(Collection<User> users) {
        this(users, false);
    }

    public MultipleUsersVisibility(User singleUser) {
        this(Collections.singleton(singleUser), true);
    }

    public MultipleUsersVisibility(Collection<User> users, boolean ignoreUserStatus) {
        super(ignoreUserStatus);
        this.users = users;
    }


    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        if(users == null || users.isEmpty()) {
            // пустая выборка
            return builder.or();
        }

        return builder.and(super.toPredicate(root, query, builder),
                           root.get(User_.ID).in(users.stream().map(User::getId).collect(Collectors.toSet())));
    }
}
