package ru.prooftechit.smh.exceptions;

import java.io.Serial;
import java.util.Collections;
import ru.prooftechit.smh.api.dto.error.ValidationErrorResponseDto;

/**
 * @author Roman Zdoronok
 */
public final class InvalidOperationException extends CommonValidationException {

    @Serial
    private static final long serialVersionUID = -560460249790883424L;

    public InvalidOperationException(String message) {
        super(Collections.singletonList(ValidationErrorResponseDto.builder()
                                                                 .message(message)
                                                                 .build()));
    }

}
