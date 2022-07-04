package ru.prooftechit.smh.service.internal;

import ru.prooftechit.smh.domain.model.Facility;
import ru.prooftechit.smh.domain.model.Hardware;

/**
 * @author Roman Zdoronok
 */
public interface HardwareServiceInternal {
    void deleteHardware(Hardware hardware);
    void deleteFacilityHardware(Facility facility);
}
