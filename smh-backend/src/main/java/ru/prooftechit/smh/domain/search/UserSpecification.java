package ru.prooftechit.smh.domain.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.api.enums.UserStatus;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.User_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class UserSpecification extends AbstractSearchSpecification<User> {

    protected Set<UserRole> roles;
    protected Set<UserStatus> statuses;

    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(super.toPredicate(root, query, builder));

        if (roles != null && !roles.isEmpty()) {
            predicates.add(root.get(User_.ROLE).in(roles));
        }

        if (statuses != null && !statuses.isEmpty()) {
            predicates.add(root.get(User_.STATUS).in(statuses));
        }

        predicates.add(builder.notEqual(root.get(User_.STATUS), UserStatus.DELETED));

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    public String[] getSearchFields() {
        return new String[]{
            User_.FIRST_NAME,
            User_.LAST_NAME,
            User_.EMAIL
        };
    }
}
