package ru.prooftechit.smh.notification.specification;


import java.io.Serial;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
public class UserVisibility extends MultipleUsersVisibility {

    @Serial
    private static final long serialVersionUID = -3247928938758324504L;

    public UserVisibility(User user) {
        super(user);
    }
}
