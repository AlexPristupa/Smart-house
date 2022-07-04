package ru.prooftechit.smh.event.model.service_work;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.prooftechit.smh.api.event.AbstractEvent;
import ru.prooftechit.smh.domain.model.ServiceWork;

/**
 * @author Roman Zdoronok
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class AbstractServiceWorkEvent extends AbstractEvent {
    private final ServiceWork serviceWork;
}
