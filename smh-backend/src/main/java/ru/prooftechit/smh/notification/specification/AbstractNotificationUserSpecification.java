package ru.prooftechit.smh.notification.specification;

import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.prooftechit.smh.api.enums.UserStatus;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.User_;

/**
 * @author Roman Zdoronok
 */
@NoArgsConstructor
public abstract class AbstractNotificationUserSpecification implements Specification<User> {

    @Serial
    private static final long serialVersionUID = -6217885826264646739L;

    /**
     * Пользователи, которых следует искличить из рассылки уведомлений.
     * Например: Юрист берет в работу обращение, всем юристам с соответствующей специализацией должно
     * быть отправлено уведомление о недоступности данного обращения. Всем, кроме текущего - ему отправляется уведомление
     * о начале работы над обращением.
     */
    private final List<User> exceptUsers = new ArrayList<>();

    /**
     * По-умолчанию уведомления могут быть отправлены только активным пользователям,
     * пока не определено иначе в конкретной спецификации.
     */
    private final Set<UserStatus> statuses = EnumSet.of(UserStatus.ACTIVE);

    protected AbstractNotificationUserSpecification(boolean ignoreUserStatus) {
        if(ignoreUserStatus) {
            ignoreUserStatus();
        }
    }

    public AbstractNotificationUserSpecification except(ExceptUsers exceptUsers) {
        this.exceptUsers.addAll(Arrays.asList(exceptUsers.getUsers()));

        return this;
    }

    public final AbstractNotificationUserSpecification activeUsers() {
        ignoreUserStatus().addUserStatus(UserStatus.ACTIVE);
        return this;
    }

    public final AbstractNotificationUserSpecification ignoreUserStatus() {
        statuses.clear();
        return this;
    }

    public final AbstractNotificationUserSpecification addUserStatus(UserStatus userStatus) {
        statuses.add(userStatus);
        return this;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder builder) {

        List<Predicate> predicates = new ArrayList<>();

        if(!exceptUsers.isEmpty()) {
            predicates.add(builder.not(root.get(User_.ID).in(exceptUsers.stream().map(User::getId).collect(Collectors.toSet()))));
        }

        if(!statuses.isEmpty()) {
            predicates.add(root.get(User_.STATUS).in(statuses));
        }

        return builder.and(predicates.toArray(new Predicate[0]));
    }


}
