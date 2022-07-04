package ru.prooftechit.smh.configuration.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.prooftechit.smh.domain.model.ServiceWorkType;
import ru.prooftechit.smh.domain.repository.ServiceWorkTypeRepository;
import ru.prooftechit.smh.exceptions.ServiceWorkTypeNotFound;

@Component
@RequiredArgsConstructor
public class ServiceWorkTypeConverter implements Converter<String, ServiceWorkType> {
    private final ServiceWorkTypeRepository serviceWorkTypeRepository;

    @Override
    public ServiceWorkType convert(String s) {
        return serviceWorkTypeRepository.findById(Long.parseLong(s)).orElseThrow(ServiceWorkTypeNotFound::new);
    }
}
