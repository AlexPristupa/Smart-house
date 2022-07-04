package ru.prooftechit.smh.domain.repository.impl;

import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;
import ru.prooftechit.smh.api.dto.PageWithUnread;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.UserEvent;
import ru.prooftechit.smh.domain.model.UserEvent_;
import ru.prooftechit.smh.domain.model.common.BaseEntity;
import ru.prooftechit.smh.domain.model.common.BaseEntity_;
import ru.prooftechit.smh.domain.model.metadata.EntityMetadata;
import ru.prooftechit.smh.domain.model.metadata.EntityMetadata_;
import ru.prooftechit.smh.domain.model.metadata.UserEventMetadata;
import ru.prooftechit.smh.domain.repository.custom.UserEventWithMetadataRepository;
import ru.prooftechit.smh.domain.search.with_metadata.UserEventMetadataSpecification;
import ru.prooftechit.smh.domain.search.with_metadata.UserEventWithMetadataSpecification;
import ru.prooftechit.smh.domain.tuple.UserEventWithMetadata;
import ru.prooftechit.smh.domain.util.PageWithUnreadImpl;

/**
 * @author Roman Zdoronok
 */
@Repository
@RequiredArgsConstructor
public class UserEventRepositoryImpl implements UserEventWithMetadataRepository {

    private final EntityManager entityManager;

    @Override
    public Optional<UserEventWithMetadata> findOneWithMetadata(UserEventMetadataSpecification specification) {
        QueryContext context = prepareContext(specification);
        try {
            return Optional.of(entityManager.createQuery(context.query).getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public PageWithUnread<UserEventWithMetadata> findAllWithMetadata(UserEventMetadataSpecification specification, Pageable pageable) {
        Objects.requireNonNull(pageable, "Должна быть указана информация о пэйджинге");

        QueryContext context = prepareContext(specification);

        Sort sort = pageable.getSort();
        if (sort.isSorted()) {
            context.query.orderBy(defineOrders(sort, context.eventRoot, context.metadataJoin, context.builder));
        }

        TypedQuery<UserEventWithMetadata> typedQuery = entityManager.createQuery(context.query);
        if (pageable.isPaged()) {
            CriteriaQuery<Long> countQuery = createCountQuery(context.builder, context.query, context.eventRoot);
            Long count = entityManager.createQuery(countQuery).getSingleResult();

            Long unread = countUnread(specification.originalSpecification(), specification.getUser());

            typedQuery.setFirstResult((int) pageable.getOffset());
            typedQuery.setMaxResults(pageable.getPageSize());
            return new PageWithUnreadImpl<>(typedQuery.getResultList(), pageable, count, unread);
        }

        List<UserEventWithMetadata> list = typedQuery.getResultList();
        return new PageWithUnreadImpl<>(list, list.stream()
                                                  .map(UserEventWithMetadata::getMetadata)
                                                  .filter(java.util.function.Predicate.not(EntityMetadata::isRead))
                                                  .count());
    }

    @Override
    public long countUnread(Specification<UserEvent> specification, User user) {
        UserEventWithMetadataSpecification metaSpec = new UserEventWithMetadataSpecification(user, specification);
        metaSpec.setRead(false);
        QueryContext context = prepareContext(metaSpec);
        CriteriaQuery<Long> countQuery = createCountQuery(context.builder, context.query, context.eventRoot);
        return entityManager.createQuery(countQuery).getSingleResult();
    }

    private QueryContext prepareContext(UserEventMetadataSpecification specification) {
        Objects.requireNonNull(specification, "Должна быть указана спецификация");

        CriteriaBuilder builder = entityManager.getCriteriaBuilder();

        CriteriaQuery<UserEventWithMetadata> query = builder.createQuery(UserEventWithMetadata.class);
        Root<UserEvent> eventRoot = query.from(UserEvent.class);
        eventRoot.alias("userEvent");
        Join<UserEvent, UserEventMetadata> metadataJoin = eventRoot.join(UserEvent_.METADATA, JoinType.LEFT);
        metadataJoin.alias("meta");

        // Ограничиваем джоин текущим пользователем
        // В документации метода .on() сказано, что текущее условие join будет заменять предыдущее.
        // Изначальное условие (по которому присоединяется связанная таблица) не заменяется
        metadataJoin.on(builder.equal(metadataJoin.get(EntityMetadata_.USER).get(BaseEntity_.ID), specification.getUser().getId()));

        query.select(builder.construct(UserEventWithMetadata.class, eventRoot, metadataJoin));

        Predicate predicate = specification.toPredicate(eventRoot, metadataJoin, query, builder);
        if (predicate != null) {
            query.where(predicate);
        }

        QueryContext context = new QueryContext();
        context.query = query;
        context.eventRoot = eventRoot;
        context.metadataJoin = metadataJoin;
        context.builder = builder;
        return context;
    }

    private <T> CriteriaQuery<Long> createCountQuery(final CriteriaBuilder cb,
                                                     final CriteriaQuery<?> criteria,
                                                     final Root<T> root) {

        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        final Root<T> countRoot = countQuery.from(root.getModel());

        doJoins(root.getJoins(), countRoot);
        doJoinsOnFetches(root.getFetches(), countRoot);

        countQuery.select(cb.countDistinct(countRoot));
        Predicate restriction = criteria.getRestriction();
        if(restriction != null) {
            countQuery.where(restriction);
        }

        countRoot.alias(root.getAlias());

        return countQuery.distinct(criteria.isDistinct());
    }

    @SuppressWarnings("unchecked")
    private void doJoinsOnFetches(Set<? extends Fetch<?, ?>> joins, Root<?> root) {
        doJoins((Set<? extends Join<?, ?>>) joins, root);
    }

    private void doJoins(Set<? extends Join<?, ?>> joins, From<?, ?> from) {
        for (Join<?, ?> join : joins) {
            Join<?, ?> joined = from.join(join.getAttribute().getName(), join.getJoinType());
            joined.alias(join.getAlias());
            joined.on(join.getOn());
            doJoins(join.getJoins(), joined);
        }
    }

    private <E extends BaseEntity<?>> List<Order> defineOrders(Sort sort,
                                                               From<E, E> from,
                                                               Join<E, ? extends EntityMetadata<E>> metadataJoin,
                                                               CriteriaBuilder builder) {
        List<Order> orders = new ArrayList<>();

        for (Sort.Order order : sort) {
            if(EntityMetadata_.DELETED.equals(order.getProperty())) {
                Expression<Boolean> expression = builder.coalesce(metadataJoin.get(EntityMetadata_.DELETED), false);
                orders.add(toJpaOrder(order, expression, builder));
            }
            else if(EntityMetadata_.READ.equals(order.getProperty())) {
                Expression<Boolean> expression = builder.coalesce(metadataJoin.get(EntityMetadata_.READ), false);
                orders.add(toJpaOrder(order, expression, builder));
            }
            else {
                orders.addAll(QueryUtils.toOrders(Sort.by(order), from, builder));
            }
        }

        return orders;
    }

    private static Order toJpaOrder(Sort.Order order, Expression<?> expression, CriteriaBuilder builder) {
        return order.isAscending() ? builder.asc(expression) : builder.desc(expression);
    }

    private static class QueryContext {
        private CriteriaQuery<UserEventWithMetadata> query;
        private Root<UserEvent> eventRoot;
        private Join<UserEvent, UserEventMetadata> metadataJoin;
        private CriteriaBuilder builder;
    }
}
