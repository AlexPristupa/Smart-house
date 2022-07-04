package ru.prooftechit.smh.domain.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.domain.model.Facility;
import ru.prooftechit.smh.domain.model.Hardware;
import ru.prooftechit.smh.domain.model.Hardware_;
import ru.prooftechit.smh.domain.model.ServiceWork_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class HardwareSpecification extends AbstractSearchSpecification<Hardware> {

    private Facility facility;

    @Override
    public Predicate toPredicate(Root<Hardware> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        //create a new predicate list
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(super.toPredicate(root, query, builder));

        if (facility != null) {
            predicates.add(builder.equal(root.get(ServiceWork_.FACILITY), facility));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    public String[] getSearchFields() {
        return new String[]{
            Hardware_.NAME,
            Hardware_.DESCRIPTION,
            Hardware_.MODEL,
            Hardware_.SERIAL_NUMBER,
            Hardware_.INSTALLER
        };
    }
}
