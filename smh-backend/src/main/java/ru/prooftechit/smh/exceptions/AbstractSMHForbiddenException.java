package ru.prooftechit.smh.exceptions;

import java.io.Serial;
import lombok.Getter;
import ru.prooftechit.smh.api.dto.error.AccessDeniedReason;

/**
 * @author Yan Yukhnovets
 */
@Getter
public abstract class AbstractSMHForbiddenException extends AbstractSMHException {

    @Serial
    private static final long serialVersionUID = -2441191130694299312L;
    private final AccessDeniedReason reason;

    public AbstractSMHForbiddenException(AccessDeniedReason reason, String message) {
        super(message);
        this.reason = reason;
    }

    public abstract Object getPayload();
}
