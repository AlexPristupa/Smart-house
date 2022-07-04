package ru.prooftechit.smh.validation.validator;

import java.util.Objects;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import ru.prooftechit.smh.api.dto.PasswordHolder;
import ru.prooftechit.smh.validation.ErrorMessages;
import ru.prooftechit.smh.validation.constraint.PasswordEquality;

@Component
public class PasswordEqualityValidator implements ConstraintValidator<PasswordEquality, PasswordHolder> {
    private static final String PASSWORD_REPEAT = "passwordRepeat";

    @Override
    public boolean isValid(PasswordHolder value, ConstraintValidatorContext context) {
        if(!Objects.equals(value.getPassword(), value.getPasswordRepeat())) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorMessages.PASSWORD_NOT_EQUALS)
                   .addPropertyNode(PASSWORD_REPEAT)
                   .addConstraintViolation();
            return false;
        }
        return true;
    }
}
