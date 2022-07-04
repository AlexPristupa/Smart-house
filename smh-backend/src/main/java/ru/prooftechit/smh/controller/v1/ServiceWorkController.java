package ru.prooftechit.smh.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftechit.smh.api.dto.ServiceWorkDto;
import ru.prooftechit.smh.api.dto.ServiceWorkRequestDto;
import ru.prooftechit.smh.api.dto.ServiceWorkTypeDto;
import ru.prooftechit.smh.api.dto.ServiceWorkTypeRequestDto;
import ru.prooftechit.smh.api.enums.ServiceWorkResolution;
import ru.prooftechit.smh.api.enums.ServiceWorkStatus;
import ru.prooftechit.smh.api.service.ServiceWorkService;
import ru.prooftechit.smh.api.v1.ServiceWorkApi;
import ru.prooftechit.smh.domain.model.ServiceWorkType;
import ru.prooftechit.smh.domain.search.ServiceWorkSpecification;
import ru.prooftechit.smh.domain.search.ServiceWorkTypeSpecification;

import java.util.Set;

/**
 * @author Roman Zdoronok
 */
@RestController
@RequiredArgsConstructor
public class ServiceWorkController extends AbstractController implements ServiceWorkApi {

    private final ServiceWorkService serviceWorkService;

    @Override
    public ResponseEntity<ServiceWorkDto> getServiceWork(Long serviceWorkId) {
        return ResponseEntity.ok(serviceWorkService.getServiceWork(serviceWorkId, getCurrentUser()));
    }

    @Override
    public ResponseEntity<?> deleteServiceWork(Long serviceWorkId) {
        serviceWorkService.deleteServiceWork(serviceWorkId, getCurrentUser());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<ServiceWorkDto> updateServiceWork(Long serviceWorkId,
                                                            ServiceWorkRequestDto serviceWork) {
        return ResponseEntity.ok(serviceWorkService.updateServiceWork(serviceWorkId, serviceWork, getCurrentUser()));
    }

    @Override
    public ResponseEntity<ServiceWorkDto> updateServiceWorkResolution(Long serviceWorkId,
                                                                      ServiceWorkResolution resolution) {
        return ResponseEntity.ok(serviceWorkService.updateServiceWorkResolution(serviceWorkId, resolution, getCurrentUser()));
    }

    @Override
    public Page<ServiceWorkDto> getServiceWorks(String search,
                                                Set<ServiceWorkStatus> statuses,
                                                ServiceWorkResolution resolution,
                                                ServiceWorkType type,
                                                Pageable pageable) {
        ServiceWorkSpecification serviceWorkSpecification = new ServiceWorkSpecification();
        serviceWorkSpecification.setStatuses(statuses)
                .setResolution(resolution)
                .setType(type)
                .setSearch(search);
        return serviceWorkService.getServiceWorks(getCurrentUser(), serviceWorkSpecification, pageable);
    }

    @Override
    public ResponseEntity<ServiceWorkTypeDto> createServiceWorkType(ServiceWorkTypeRequestDto serviceWorkType) {
        return ResponseEntity.ok(serviceWorkService.saveServiceWorkType(serviceWorkType));
    }

    @Override
    public ResponseEntity<ServiceWorkTypeDto> updateServiceWorkType(Long serviceWorkTypeId,
                                                                    ServiceWorkTypeRequestDto serviceWorkType) {
        return ResponseEntity.ok(serviceWorkService.updateServiceWorkType(serviceWorkTypeId, serviceWorkType));
    }

    @Override
    public Page<ServiceWorkTypeDto> getServiceWorkTypes(String search, Pageable pageable) {
        ServiceWorkTypeSpecification serviceWorkTypeSpecification = new ServiceWorkTypeSpecification();
        serviceWorkTypeSpecification.setSearch(search);
        return serviceWorkService.getServiceWorkTypes(serviceWorkTypeSpecification, pageable);
    }

    @Override
    public ResponseEntity<?> deleteServiceWorkType(Long serviceWorkTypeId) {
        serviceWorkService.deleteServiceWorkType(serviceWorkTypeId, getCurrentUser());
        return ResponseEntity.noContent().build();
    }
}
