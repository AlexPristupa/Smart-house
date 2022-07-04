package ru.prooftechit.smh.validation.validator;

import java.util.Set;
import java.util.UUID;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.prooftechit.smh.domain.repository.FileRepository;
import ru.prooftechit.smh.validation.constraint.ContentType;

@Component
@RequiredArgsConstructor
public class FileContentTypeValidator implements ConstraintValidator<ContentType, UUID> {
    private final FileRepository fileRepository;

    private String[] contentTypes;

    @Override
    public void initialize(ContentType constraintAnnotation) {
        contentTypes = constraintAnnotation.mediaTypes();
    }

    @Override
    public boolean isValid(UUID contentId, ConstraintValidatorContext context) {
        return contentId == null
            || contentTypes == null
            || fileRepository.existsByContentIdAndMimeTypeIn(contentId, Set.of(contentTypes));
    }
}
