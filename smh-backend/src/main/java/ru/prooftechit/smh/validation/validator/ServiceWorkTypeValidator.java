package ru.prooftechit.smh.validation.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.prooftechit.smh.domain.repository.ServiceWorkTypeRepository;
import ru.prooftechit.smh.validation.constraint.ServiceWorkType;

@Component
@RequiredArgsConstructor
public class ServiceWorkTypeValidator implements ConstraintValidator<ServiceWorkType, Long> {
    private final ServiceWorkTypeRepository serviceWorkTypeRepository;

    @Override
    public boolean isValid(Long serviceWorkTypeId, ConstraintValidatorContext context) {
        return serviceWorkTypeId == null
            || serviceWorkTypeRepository.existsById(serviceWorkTypeId);
    }
}
