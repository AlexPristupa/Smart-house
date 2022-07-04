package ru.prooftechit.smh.event.model.hardware;

import ru.prooftechit.smh.domain.model.Hardware;

/**
 * @author Roman Zdoronok
 */
public class HardwareCreatedEvent extends AbstractHardwareEvent {
    public HardwareCreatedEvent(Hardware hardware) {
        super(hardware);
    }
}
