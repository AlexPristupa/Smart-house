package ru.prooftechit.smh.exceptions.file;


import ru.prooftechit.smh.exceptions.AbstractSMHException;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Roman Zdoronok
 */
public final class FailedToSaveFileException extends AbstractSMHException {

    private static final long serialVersionUID = -2701685373066462662L;

    public FailedToSaveFileException() {
        super(ErrorMessages.FAILED_TO_SAVE_A_FILE);
    }

    public FailedToSaveFileException(Throwable t) {
        super(ErrorMessages.FAILED_TO_SAVE_A_FILE);
    }
}
