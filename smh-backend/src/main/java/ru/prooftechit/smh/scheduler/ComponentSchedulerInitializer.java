package ru.prooftechit.smh.scheduler;

import ru.prooftechit.smh.api.service.SchedulerService;

/**
 * @author Roman Zdoronok
 */
public interface ComponentSchedulerInitializer {

    String getComponentName();
    int initScheduler(SchedulerService schedulerService);

}
