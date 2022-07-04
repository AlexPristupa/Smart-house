package ru.prooftechit.smh.exceptions;

import java.io.Serial;
import java.util.Collection;
import java.util.Collections;
import ru.prooftechit.smh.api.dto.error.ValidationErrorResponseDto;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Roman Zdoronok
 */
public final class ShouldBeInStatusException extends CommonValidationException {

    @Serial
    private static final long serialVersionUID = -4380636921286554017L;

    public ShouldBeInStatusException(String fieldName, Collection<Enum<?>> statuses) {
        this(fieldName, statuses, false);
    }

    public ShouldBeInStatusException(String fieldName, Collection<Enum<?>> statuses, boolean notInStatus) {
        super(Collections.singletonList(ValidationErrorResponseDto.builder()
                                                                 .message(notInStatus
                                                                          ? ErrorMessages.SHOULD_NOT_BE_IN_STATUS
                                                                          : ErrorMessages.SHOULD_BE_IN_STATUS)
                                                                 .fieldName(fieldName)
                                                                 .payload(statuses)
                                                                 .build()));
    }

}
