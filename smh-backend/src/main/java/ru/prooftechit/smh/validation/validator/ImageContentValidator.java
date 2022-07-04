package ru.prooftechit.smh.validation.validator;

import java.util.Arrays;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.constants.MediaTypes;
import ru.prooftechit.smh.validation.constraint.ImageMultipartFile;

@Component
@RequiredArgsConstructor
public class ImageContentValidator implements ConstraintValidator<ImageMultipartFile, MultipartFile> {

    @Override
    public boolean isValid(MultipartFile multipartFile, ConstraintValidatorContext context) {
        return multipartFile == null
            || multipartFile.isEmpty()
            || Arrays.asList(MediaTypes.IMAGE_TYPES)
                     .contains(multipartFile.getContentType());
    }
}
