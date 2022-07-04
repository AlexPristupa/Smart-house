package ru.prooftechit.smh.domain.search.with_metadata;

import javax.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;
import ru.prooftechit.smh.domain.tuple.EntityWithMetadata;

/**
 * @author Roman Zdoronok
 */
public interface WithMetadataSpecification<E, M, T extends EntityWithMetadata<E, M>> {

    Predicate toPredicate(Root<E> root,
                          Join<E, M> metadataJoin,
                          CriteriaQuery<T> query,
                          CriteriaBuilder criteriaBuilder);

    Specification<E> originalSpecification();

}
