package ru.prooftechit.smh.notification.specification;

import java.io.Serial;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
public class GlobalVisibility extends AbstractNotificationUserSpecification {

    @Serial
    private static final long serialVersionUID = 1644698394563794907L;

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        // Никаких ограничений или критериев поиска. Кроме исключений в родительском классе.
        return super.toPredicate(root, query, builder);
    }
}
