package ru.prooftechit.smh.event.model.service_work;

import ru.prooftechit.smh.domain.model.ServiceWork;

/**
 * @author Roman Zdoronok
 */
public class ServiceWorkDeletedEvent extends AbstractServiceWorkEvent {
    public ServiceWorkDeletedEvent(ServiceWork serviceWork) {
        super(serviceWork);
    }
}
