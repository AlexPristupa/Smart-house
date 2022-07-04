package ru.prooftechit.smh.exceptions.file;


import ru.prooftechit.smh.exceptions.AbstractSMHNotFoundException;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Andrey Kovalenko
 */
public final class ContentNotFoundException extends AbstractSMHNotFoundException {

    private static final long serialVersionUID = -5246579923145613345L;

    public ContentNotFoundException() {
        super(ErrorMessages.CONTENT_NOT_FOUND);
    }
}
