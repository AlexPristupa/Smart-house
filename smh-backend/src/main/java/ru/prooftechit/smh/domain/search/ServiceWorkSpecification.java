package ru.prooftechit.smh.domain.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.api.enums.ServiceWorkResolution;
import ru.prooftechit.smh.api.enums.ServiceWorkStatus;
import ru.prooftechit.smh.domain.model.*;

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
public class ServiceWorkSpecification extends AbstractSearchSpecification<ServiceWork> {

    private Facility facility;
    private Set<ServiceWorkStatus> statuses;
    private ServiceWorkResolution resolution;
    private ServiceWorkType type;

    @Override
    public Predicate toPredicate(Root<ServiceWork> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();

        predicates.add(super.toPredicate(root, query, builder));

        if (statuses != null && !statuses.isEmpty()) {
            predicates.add(root.get(ServiceWork_.STATUS).in(statuses));
        }

        if (resolution != null) {
            predicates.add(builder.equal(root.get(ServiceWork_.RESOLUTION), resolution));
        }

        if (type != null) {
            predicates.add(builder.equal(root.get(ServiceWork_.TYPE), type));
        }

        if (facility != null) {
            predicates.add(builder.equal(root.get(ServiceWork_.FACILITY), facility));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    public String[] getSearchFields() {
        return new String[]{
            ServiceWork_.NAME,
            ServiceWork_.DESCRIPTION
        };
    }
}
