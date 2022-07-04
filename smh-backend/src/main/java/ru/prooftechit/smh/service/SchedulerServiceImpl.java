package ru.prooftechit.smh.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.api.service.SchedulerService;
import ru.prooftechit.smh.domain.model.Hardware;
import ru.prooftechit.smh.domain.model.ServiceWork;
import ru.prooftechit.smh.scheduler.hardware.HardwareScheduler;
import ru.prooftechit.smh.scheduler.service_work.ServiceWorkScheduler;

/**
 * @author Roman Zdoronok
 */
@Service
@RequiredArgsConstructor
public class SchedulerServiceImpl implements SchedulerService {

    private final ServiceWorkScheduler serviceWorkScheduler;
    private final HardwareScheduler hardwareScheduler;

    @Override
    public void scheduleServiceWorkNotifications(ServiceWork serviceWork) {
        serviceWorkScheduler.schedule(serviceWork);
    }

    @Override
    public void cancelServiceWorkNotifications(Long serviceWorkId) {
        serviceWorkScheduler.cancel(serviceWorkId);
    }

    @Override
    public void scheduleHardwareNotifications(Hardware hardware) {
        hardwareScheduler.schedule(hardware);
    }

    @Override
    public void cancelHardwareNotifications(Long hardwareId) {
        hardwareScheduler.cancel(hardwareId);
    }
}
