package ru.prooftechit.smh.exceptions;

import java.io.Serial;
import org.springframework.http.HttpStatus;

/**
 * Предок для исключений, требующих отправки статуса {@link HttpStatus#GONE} клиенту
 *
 * @author Roman Zdoronok
 */
public abstract class AbstractSMHResourceGoneException extends AbstractSMHException {

    @Serial
    private static final long serialVersionUID = -8092531454216540769L;

    public AbstractSMHResourceGoneException(String message) {
        super(message);
    }
}
