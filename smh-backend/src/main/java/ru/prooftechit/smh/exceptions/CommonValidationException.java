package ru.prooftechit.smh.exceptions;

import java.io.Serial;
import java.util.Collections;
import java.util.List;
import ru.prooftechit.smh.api.dto.error.ValidationErrorResponseDto;

/**
 * @author Roman Zdoronok
 */
public class CommonValidationException extends AbstractSMHException {

    @Serial
    private static final long serialVersionUID = -5929367324508930155L;

    private final List<ValidationErrorResponseDto> errors;

    public CommonValidationException(ValidationErrorResponseDto singleError) {
        this(Collections.singletonList(singleError));
    }

    public CommonValidationException(List<ValidationErrorResponseDto> multipleErrors) {
        super("");
        this.errors = multipleErrors;
    }

    public List<ValidationErrorResponseDto> getErrors() {
        return errors;
    }
}
