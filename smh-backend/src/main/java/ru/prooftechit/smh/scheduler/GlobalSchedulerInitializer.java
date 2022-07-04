package ru.prooftechit.smh.scheduler;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.prooftechit.smh.api.service.SchedulerService;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class GlobalSchedulerInitializer {

    private final Scheduler scheduler;
    private final SchedulerService schedulerService;

    private final List<ComponentSchedulerInitializer> componentSchedulerInitializers;

    @EventListener
    public void onApplicationStarted(ApplicationStartedEvent event) throws SchedulerException {

        log.info("Инициализируем планировщик заданий...");
        log.info("Категорий задач для инициализации: {}", componentSchedulerInitializers.size());

        for (ComponentSchedulerInitializer initializer : componentSchedulerInitializers) {
            log.info("- {}...", initializer.getComponentName());
            int tasksScheduled = initializer.initScheduler(schedulerService);
            log.info("\tзадач поставлено в очередь: {}", tasksScheduled);
        }

        log.info("Планировщик заданий инициализирован.");
        if (!scheduler.isStarted()) {
            scheduler.start();
            log.info("Планировщик заданий запущен.");
        }
    }

}
