package ru.prooftechit.smh.service.internal;

import ru.prooftechit.smh.domain.model.Facility;
import ru.prooftechit.smh.domain.model.ServiceWork;
import ru.prooftechit.smh.domain.model.ServiceWorkType;

/**
 * @author Roman Zdoronok
 */
public interface ServiceWorkServiceInternal {
    void deleteServiceWork(ServiceWork serviceWork);
    void deleteFacilityServiceWorks(Facility facility);
    void deleteServiceWorkType(ServiceWorkType serviceWork);
}
