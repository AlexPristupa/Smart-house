package ru.prooftechit.smh.event.model.service_work;

import lombok.Getter;
import ru.prooftechit.smh.domain.model.ServiceWork;

/**
 * @author Roman Zdoronok
 */
public class ServiceWorkFinishedEvent extends AbstractServiceWorkEvent {
    public ServiceWorkFinishedEvent(ServiceWork serviceWork) {
        super(serviceWork);
    }
}
