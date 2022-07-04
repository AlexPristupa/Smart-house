package ru.prooftechit.smh.exceptions;

import java.io.Serial;
import java.util.Collections;
import ru.prooftechit.smh.api.dto.error.ValidationErrorResponseDto;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Roman Zdoronok
 */
public final class AmbiguousRequestException extends CommonValidationException {

    @Serial
    private static final long serialVersionUID = 3947335534662767862L;

    public AmbiguousRequestException(String fieldName) {
        super(Collections.singletonList(ValidationErrorResponseDto.builder()
                                                                 .message(ErrorMessages.AMBIGUOUS_REQUEST)
                                                                 .fieldName(fieldName)
                                                                 .build()));
    }

}
