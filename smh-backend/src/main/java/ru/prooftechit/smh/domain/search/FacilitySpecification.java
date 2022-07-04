package ru.prooftechit.smh.domain.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.domain.model.Facility;
import ru.prooftechit.smh.domain.model.Facility_;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
public class FacilitySpecification extends AbstractSearchSpecification<Facility> {

    @Override
    public Predicate toPredicate(Root<Facility> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        return super.toPredicate(root, query, builder);
    }

    @Override
    public String[] getSearchFields() {
        return new String[]{
            Facility_.NAME,
            Facility_.DESCRIPTION,
            Facility_.ADDRESS
        };
    }
}
