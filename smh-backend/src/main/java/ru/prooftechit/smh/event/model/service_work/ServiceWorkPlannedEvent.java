package ru.prooftechit.smh.event.model.service_work;

import lombok.Getter;
import ru.prooftechit.smh.domain.model.ServiceWork;

/**
 * @author Roman Zdoronok
 */
public class ServiceWorkPlannedEvent extends AbstractServiceWorkEvent {
    public ServiceWorkPlannedEvent(ServiceWork serviceWork) {
        super(serviceWork);
    }
}
