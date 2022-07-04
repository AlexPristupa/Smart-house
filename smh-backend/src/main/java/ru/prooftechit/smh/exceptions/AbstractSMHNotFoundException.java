package ru.prooftechit.smh.exceptions;

import java.io.Serial;
import org.springframework.http.HttpStatus;

/**
 * Предок для исключений, требующих отправки статуса {@link HttpStatus#NOT_FOUND} клиенту
 *
 * @author Andrey Kovalenko
 */
public abstract class AbstractSMHNotFoundException extends AbstractSMHException {

    @Serial
    private static final long serialVersionUID = 3217614981538787760L;

    public AbstractSMHNotFoundException(String message) {
        super(message);
    }
}
