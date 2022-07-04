package ru.prooftechit.smh.event.model.hardware;

import ru.prooftechit.smh.domain.model.Hardware;

/**
 * @author Roman Zdoronok
 */
public class HardwareDeletedEvent extends AbstractHardwareEvent {
    public HardwareDeletedEvent(Hardware hardware) {
        super(hardware);
    }
}
