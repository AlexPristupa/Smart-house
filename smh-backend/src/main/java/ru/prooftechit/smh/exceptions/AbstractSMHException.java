package ru.prooftechit.smh.exceptions;

import java.io.Serial;

/**
 * @author Roman Zdoronok
 */
public abstract class AbstractSMHException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 4882151366624987142L;

    public AbstractSMHException(String message) {
        super(message);
    }

    public AbstractSMHException(String message, Throwable t) {
        super(message, t);
    }

}
