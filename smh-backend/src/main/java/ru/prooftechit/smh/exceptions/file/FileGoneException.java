package ru.prooftechit.smh.exceptions.file;


import ru.prooftechit.smh.exceptions.AbstractSMHResourceGoneException;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Roman Zdoronok
 */
public class FileGoneException extends AbstractSMHResourceGoneException {

    private static final long serialVersionUID = -9021076535204926551L;

    public FileGoneException() {
        super(ErrorMessages.CONTENT_GONE);
    }
}
