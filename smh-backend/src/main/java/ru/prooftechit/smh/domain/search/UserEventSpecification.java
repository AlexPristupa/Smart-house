package ru.prooftechit.smh.domain.search;

import java.io.Serial;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ru.prooftechit.smh.domain.model.UserEvent;

/**
 * @author Roman Zdoronok
 */
public class UserEventSpecification implements Specification<UserEvent> {

    @Serial
    private static final long serialVersionUID = 3891437707369850024L;

    @Override
    public Predicate toPredicate(Root<UserEvent> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return builder.conjunction();
    }
}
