package ru.prooftechit.smh.exceptions;

import ru.prooftechit.smh.api.dto.error.AccessDeniedReason;
import ru.prooftechit.smh.validation.ErrorMessages;

import java.io.Serial;

/**
 * @author Roman Zdoronok
 */
public class RestrictedAccessException extends AbstractSMHForbiddenException {

    @Serial
    private static final long serialVersionUID = 6539796085004835249L;

    public RestrictedAccessException() {
        this(ErrorMessages.RECORD_NOT_ACCESSIBLE);
    }
    
    public RestrictedAccessException(String message) {
        super(AccessDeniedReason.RESTRICTED_ACCESS, message);
    }

    @Override
    public Object getPayload() {
        return null;
    }
}
