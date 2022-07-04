package ru.prooftechit.smh.validation.validator;

import java.util.UUID;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.prooftechit.smh.domain.repository.FileRepository;
import ru.prooftechit.smh.validation.constraint.ContentId;

@Component
@RequiredArgsConstructor
public class FileContentIdValidator implements ConstraintValidator<ContentId, UUID> {
    private final FileRepository fileRepository;

    @Override
    public boolean isValid(UUID contentId, ConstraintValidatorContext context) {
        return contentId == null || fileRepository.existsByContentId(contentId);
    }
}
