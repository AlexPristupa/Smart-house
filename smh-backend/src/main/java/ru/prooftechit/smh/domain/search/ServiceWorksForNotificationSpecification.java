package ru.prooftechit.smh.domain.search;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import ru.prooftechit.smh.api.enums.ServiceWorkStatus;
import ru.prooftechit.smh.domain.model.ServiceWork;
import ru.prooftechit.smh.domain.model.ServiceWork_;

/**
 * @author Roman Zdoronok
 */
public class ServiceWorksForNotificationSpecification implements Specification<ServiceWork> {

    @Override
    public Predicate toPredicate(Root<ServiceWork> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();

        // Сервисная работа должна быть не завершена
        predicates.add(root.get(ServiceWork_.STATUS).in(ServiceWorkStatus.PENDING, ServiceWorkStatus.IN_PROGRESS));

        return builder.and(predicates.toArray(new Predicate[0]));
    }
}
