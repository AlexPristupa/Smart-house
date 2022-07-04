package ru.prooftechit.smh.controller.v1.facility;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftechit.smh.api.dto.ServiceWorkDto;
import ru.prooftechit.smh.api.dto.ServiceWorkRequestDto;
import ru.prooftechit.smh.api.enums.ServiceWorkResolution;
import ru.prooftechit.smh.api.enums.ServiceWorkStatus;
import ru.prooftechit.smh.api.service.ServiceWorkService;
import ru.prooftechit.smh.api.v1.facility.FacilityServiceWorkApi;
import ru.prooftechit.smh.controller.v1.AbstractController;
import ru.prooftechit.smh.domain.model.ServiceWorkType;
import ru.prooftechit.smh.domain.search.ServiceWorkSpecification;

import java.util.Set;

/**
 * @author Roman Zdoronok
 */
@RestController
@RequiredArgsConstructor
public class FacilityServiceWorkController extends AbstractController implements FacilityServiceWorkApi {

    private final ServiceWorkService serviceWorkService;

    @Override
    public ResponseEntity<ServiceWorkDto> createServiceWork(Long facilityId, ServiceWorkRequestDto serviceWork) {
        return ResponseEntity.ok(serviceWorkService.saveServiceWork(facilityId, serviceWork, getCurrentUser()));
    }

    @Override
    public Page<ServiceWorkDto> getServiceWorks(Long facilityId,
                                                String search,
                                                Set<ServiceWorkStatus> statuses,
                                                ServiceWorkResolution resolution,
                                                ServiceWorkType type,
                                                Pageable pageable) {
        ServiceWorkSpecification serviceWorkSpecification = new ServiceWorkSpecification();
        serviceWorkSpecification.setStatuses(statuses)
            .setStatuses(statuses)
            .setResolution(resolution)
            .setType(type)
            .setSearch(search);
        return serviceWorkService.getServiceWorks(facilityId, getCurrentUser(), serviceWorkSpecification, pageable);
    }
}
