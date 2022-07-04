package ru.prooftechit.smh.exceptions;


import ru.prooftechit.smh.validation.ErrorMessages;

import java.io.Serial;

/**
 * @author Roman Zdoronok
 */
public final class RecordNotFoundException extends AbstractSMHNotFoundException {

    @Serial
    private static final long serialVersionUID = -1400371201119171072L;

    public RecordNotFoundException() {
        super(ErrorMessages.RECORD_NOT_FOUND);
    }
}
