package ru.prooftechit.smh.validation.constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import ru.prooftechit.smh.validation.ErrorMessages;
import ru.prooftechit.smh.validation.validator.NotEmptyMultipartFileValidator;

/**
 * @author Roman Zdoronok
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE_USE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyMultipartFileValidator.class)
public @interface NotEmptyMultipartFile {
    String message() default ErrorMessages.FILE_EMPTY;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
