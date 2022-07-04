package ru.prooftechit.smh.event.model.hardware;

import ru.prooftechit.smh.domain.model.Hardware;

public class HardwareChangedEvent extends AbstractHardwareEvent {
    public HardwareChangedEvent(Hardware hardware) {
        super(hardware);
    }
}
