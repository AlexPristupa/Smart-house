package ru.prooftechit.smh.validation.constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import ru.prooftechit.smh.validation.ErrorMessages;
import ru.prooftechit.smh.validation.validator.FileContentIdValidator;

/**
 * @author Roman Zdoronok
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE_USE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileContentIdValidator.class)
public @interface ContentId {
    String message() default ErrorMessages.FILE_NOT_EXIST;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
