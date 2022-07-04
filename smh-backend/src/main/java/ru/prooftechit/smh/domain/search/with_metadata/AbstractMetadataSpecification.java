package ru.prooftechit.smh.domain.search.with_metadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.Specification;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.common.BaseEntity;
import ru.prooftechit.smh.domain.model.metadata.EntityMetadata;
import ru.prooftechit.smh.domain.model.metadata.EntityMetadata_;
import ru.prooftechit.smh.domain.tuple.EntityWithMetadata;

/**
 * @author Roman Zdoronok
 */
@Getter
@Setter
@RequiredArgsConstructor
@Accessors(chain = true)
public abstract class AbstractMetadataSpecification
    <E extends BaseEntity<?>,
        M extends EntityMetadata<E>,
        T extends EntityWithMetadata<E, M>>
    implements WithMetadataSpecification<E, M, T> {

    private final User user;
    private final Specification<E> specification;

    private Boolean deleted = false;
    private Boolean read = null;

    @Override
    public Predicate toPredicate(Root<E> root,
                                 Join<E, M> metadataJoin,
                                 CriteriaQuery<T> query,
                                 CriteriaBuilder builder) {
        // create a new predicate list
        List<Predicate> predicates = new ArrayList<>();

        // Необходимо предусмотреть случай, когда в таблице метаданных нет записи для текущего пользователя.
        // В таком случае значения метаданных должны браться по-умолчанию

        if (deleted != null) {
            if (deleted) {
                predicates.add(builder.isTrue(metadataJoin.get(EntityMetadata_.DELETED)));
            }
            else {
                predicates.add(builder.or(
                    builder.isNull(metadataJoin.get(EntityMetadata_.DELETED)),
                    builder.isFalse(metadataJoin.get(EntityMetadata_.DELETED))
                ));
            }

        }

        if (read != null) {
            if (read) {
                predicates.add(builder.isTrue(metadataJoin.get(EntityMetadata_.READ)));
            }
            else {
                predicates.add(builder.or(
                    builder.isNull(metadataJoin.get(EntityMetadata_.READ)),
                    builder.isFalse(metadataJoin.get(EntityMetadata_.READ))
                ));
            }

        }

        if (specification != null) {
            predicates.add(specification.toPredicate(root, query, builder));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }

    @Override
    public Specification<E> originalSpecification() {
        return specification;
    }

    public void setUnread(Boolean unread) {
        read = Optional.ofNullable(unread).map(aBoolean -> !aBoolean).orElse(null);
    }
}
