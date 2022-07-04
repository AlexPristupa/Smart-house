package ru.prooftechit.smh.api.service;

import ru.prooftechit.smh.domain.model.Hardware;
import ru.prooftechit.smh.domain.model.ServiceWork;

/**
 * @author Roman Zdoronok
 */
public interface SchedulerService {

    void scheduleServiceWorkNotifications(ServiceWork serviceWork);
    void cancelServiceWorkNotifications(Long serviceWorkId);

    void scheduleHardwareNotifications(Hardware hardware);
    void cancelHardwareNotifications(Long hardwareId);

}
