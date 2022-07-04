package ru.prooftechit.smh.exceptions;

import ru.prooftechit.smh.validation.ErrorMessages;

public final class ServiceWorkTypeNotFound extends AbstractSMHNotFoundException {
    public ServiceWorkTypeNotFound() {
        super(ErrorMessages.TYPE_NOT_FOUND);
    }
}
