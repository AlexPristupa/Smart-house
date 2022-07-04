package ru.prooftechit.smh.notification.specification;

import lombok.Getter;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
@Getter
public class ExceptUsers {
    private final User[] users;

    public ExceptUsers(User... users) {
        this.users = users;
    }

}
