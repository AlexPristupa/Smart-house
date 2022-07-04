package ru.prooftechit.smh.domain.search;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.Specification;

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
public abstract class AbstractSearchSpecification<E> implements Specification<E> {

    private String search;

    public Predicate toPredicate(Root<E> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        List<Predicate> predicates = new ArrayList<>();
        if (search != null && !search.isEmpty()) {
            for (String searchField : getSearchFields()) {
                predicates.add(builder.or(
                    builder.like(builder.lower(root.get(searchField)), "%" + search.toLowerCase() + "%")
                ));
            }
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }



    public abstract String[] getSearchFields();

}
