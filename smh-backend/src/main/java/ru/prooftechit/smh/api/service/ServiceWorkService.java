package ru.prooftechit.smh.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.prooftechit.smh.api.dto.ServiceWorkDto;
import ru.prooftechit.smh.api.dto.ServiceWorkRequestDto;
import ru.prooftechit.smh.api.dto.ServiceWorkTypeDto;
import ru.prooftechit.smh.api.dto.ServiceWorkTypeRequestDto;
import ru.prooftechit.smh.api.enums.ServiceWorkResolution;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.search.ServiceWorkSpecification;
import ru.prooftechit.smh.domain.search.ServiceWorkTypeSpecification;

/**
 * @author Roman Zdoronok
 */
public interface ServiceWorkService {
    ServiceWorkTypeDto saveServiceWorkType(ServiceWorkTypeRequestDto serviceWorkTypeRequestDto);
    ServiceWorkTypeDto updateServiceWorkType(long serviceWorkTypeId, ServiceWorkTypeRequestDto serviceWorkTypeRequestDto);
    Page<ServiceWorkTypeDto> getServiceWorkTypes(ServiceWorkTypeSpecification specification, Pageable pageable);

    ServiceWorkDto saveServiceWork(long facilityId, ServiceWorkRequestDto serviceWorkRequestDto, User user);
    ServiceWorkDto updateServiceWork(long serviceWorkId, ServiceWorkRequestDto serviceWorkRequestDto, User user);
    ServiceWorkDto updateServiceWorkResolution(long serviceWorkId, ServiceWorkResolution resolution, User user);

    ServiceWorkDto getServiceWork(long serviceWorkId);
    ServiceWorkDto getServiceWork(long serviceWorkId, User user);
    void deleteServiceWork(long serviceWorkId, User user);
    void deleteServiceWorkType(long serviceWorkTypeId, User user);
    Page<ServiceWorkDto> getServiceWorks(User user, ServiceWorkSpecification specification, Pageable pageable);
    Page<ServiceWorkDto> getServiceWorks(long facilityId, User user, ServiceWorkSpecification specification, Pageable pageable);

    void startServiceWork(long serviceWorkId);
    void finishServiceWork(long serviceWorkId);
}
