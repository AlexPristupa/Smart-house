package ru.prooftechit.smh.event.model.service_work;

import lombok.Getter;
import ru.prooftechit.smh.api.enums.ServiceWorkResolution;
import ru.prooftechit.smh.domain.model.ServiceWork;

/**
 * @author Roman Zdoronok
 */
@Getter
public class ServiceWorkResolutionEvent extends AbstractServiceWorkEvent {
    private final ServiceWorkResolution resolution;
    public ServiceWorkResolutionEvent(ServiceWorkResolution resolution,
                                      ServiceWork serviceWork) {
        super(serviceWork);
        this.resolution = resolution;
    }
}
