package ru.prooftechit.smh.exceptions.user;


import java.io.Serial;
import ru.prooftechit.smh.exceptions.AbstractSMHNotFoundException;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Roman Zdoronok
 */
public final class UserNotFoundException extends AbstractSMHNotFoundException {

    @Serial
    private static final long serialVersionUID = 3705935878761491612L;

    public UserNotFoundException() {
        super(ErrorMessages.USER_NOT_FOUND);
    }
}
