package ru.prooftechit.smh.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.validation.constraint.NotEmptyMultipartFile;

@Component
@RequiredArgsConstructor
public class NotEmptyMultipartFileValidator implements ConstraintValidator<NotEmptyMultipartFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        return multipartFile == null
            || !multipartFile.isEmpty();
    }
}
