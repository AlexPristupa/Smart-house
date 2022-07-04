package ru.prooftechit.smh.validation.constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import ru.prooftechit.smh.validation.ErrorMessages;
import ru.prooftechit.smh.validation.validator.PasswordEqualityValidator;

@Constraint(validatedBy = PasswordEqualityValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordEquality {

    String message() default ErrorMessages.PASSWORD_NOT_EQUALS;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
