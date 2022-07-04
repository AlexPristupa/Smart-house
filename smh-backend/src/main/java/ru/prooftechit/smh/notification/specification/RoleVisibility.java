package ru.prooftechit.smh.notification.specification;

import java.io.Serial;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.User_;

/**
 * @author Roman Zdoronok
 */
@RequiredArgsConstructor
@Getter
public class RoleVisibility extends AbstractNotificationUserSpecification {

    @Serial
    private static final long serialVersionUID = -7775770979490121588L;

    private final UserRole role;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.and(super.toPredicate(root, query, builder),
                           builder.equal(root.get(User_.ROLE), role));
    }
}
