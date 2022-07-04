package ru.prooftechit.smh.validation.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import ru.prooftechit.smh.api.dto.error.AccessDeniedReason;
import ru.prooftechit.smh.api.dto.error.ForbiddenErrorResponseDto;
import ru.prooftechit.smh.api.dto.error.ValidationErrorResponseDto;
import ru.prooftechit.smh.exceptions.AbstractSMHForbiddenException;
import ru.prooftechit.smh.exceptions.AbstractSMHNotFoundException;
import ru.prooftechit.smh.exceptions.AbstractSMHException;
import ru.prooftechit.smh.exceptions.AbstractSMHResourceGoneException;
import ru.prooftechit.smh.exceptions.CommonValidationException;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * Обработчик ошибок валидации.
 *
 * @author Aleksandr Kosich
 */
@ControllerAdvice
public class ValidationErrorHandler {

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ValidationErrorResponseDto> processFormDataValidationError(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ValidationErrorResponseDto> validationErrors = new ArrayList<>(bindingResult.getAllErrors().size());
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(error -> validationErrors.add(
                ValidationErrorResponseDto.builder()
                                          .message(error.getDefaultMessage())
                                          .fieldName(((FieldError) error).getField())
                                          .rejectedValue(ValidationErrorHandler.rejectedValue(error))
                                          .formName(error.getObjectName())
                                          .build()
            ));
        }

        return validationErrors;
    }

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ValidationErrorResponseDto> handleJsonProcessingException(InvalidFormatException e) {
        Reference path = e.getPath().get(e.getPath().size() - 1);
        Object from = path.getFrom();
        Class<?> cls = (from instanceof Class<?>) ? (Class<?>)from : from.getClass();
        return Collections.singletonList(ValidationErrorResponseDto.builder()
                                                                   .message(ErrorMessages.INVALID_FORMAT)
                                                                   .fieldName(path.getFieldName())
                                                                   .formName(cls.getSimpleName())
                                                                   .rejectedValue(e.getValue().toString())
                                                                   .build());
    }

    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ValidationErrorResponseDto> handleJsonProcessingException(JsonProcessingException e) {
        return Collections.singletonList(ValidationErrorResponseDto.builder()
                                                                   .message(e.getMessage())
                                                                   .build());
    }

    @ExceptionHandler(CommonValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    List<ValidationErrorResponseDto> processUserActionValidationError(CommonValidationException e) {
        return e.getErrors();
    }


    @ExceptionHandler(AbstractSMHException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ValidationErrorResponseDto> processUserActionValidationError(AbstractSMHException e) {
        return Collections.singletonList(ValidationErrorResponseDto.builder()
                                                                   .message(e.getMessage())
                                                                   .build());
    }

    @ExceptionHandler(AbstractSMHForbiddenException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    public ForbiddenErrorResponseDto processForbiddenException(AbstractSMHForbiddenException e) {
        return ForbiddenErrorResponseDto.builder()
                                        .message(e.getMessage())
                                        .reason(e.getReason())
                                        .payload(e.getPayload())
                                        .build();
    }

    @ExceptionHandler(AbstractSMHNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public List<ValidationErrorResponseDto> processUserActionValidationError(AbstractSMHNotFoundException e) {
        return Collections.singletonList(ValidationErrorResponseDto.builder()
                                                                   .message(e.getMessage())
                                                                   .build());
    }

    @ExceptionHandler(AbstractSMHResourceGoneException.class)
    @ResponseStatus(HttpStatus.GONE)
    @ResponseBody
    public List<ValidationErrorResponseDto> processResourceGoneException(AbstractSMHResourceGoneException e) {
        return Collections.singletonList(ValidationErrorResponseDto.builder()
                                                                   .message(e.getMessage())
                                                                   .build());
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public List<ValidationErrorResponseDto> processRequestPartMissing(MissingServletRequestPartException e) {
        return Collections.singletonList(ValidationErrorResponseDto.builder()
                                                                   .fieldName(e.getRequestPartName())
                                                                   .message(ErrorMessages.EMPTY_FIELD)
                                                                   .build());
    }

    private static String rejectedValue(ObjectError error) {
        if (error instanceof FieldError) {
            Object rejectedValue = ((FieldError) error).getRejectedValue();
            if (rejectedValue instanceof MultipartFile) {
                return ((MultipartFile) rejectedValue).getOriginalFilename();
            }
            return String.valueOf(rejectedValue);
        }
        return null;
    }
}
