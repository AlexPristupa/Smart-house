package ru.prooftechit.smh.scheduler.service_work;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.prooftechit.smh.api.service.SchedulerService;
import ru.prooftechit.smh.domain.model.ServiceWork;
import ru.prooftechit.smh.domain.repository.ServiceWorkRepository;
import ru.prooftechit.smh.domain.search.ServiceWorksForNotificationSpecification;
import ru.prooftechit.smh.domain.util.PagedRepositoryReader;
import ru.prooftechit.smh.scheduler.ComponentSchedulerInitializer;

/**
 * @author Roman Zdoronok
 */
@Order(1)
@Component
@AllArgsConstructor
public class ServiceWorkInitializer implements ComponentSchedulerInitializer {
    private final ServiceWorkRepository serviceWorkRepository;

    @Override
    public String getComponentName() {
        return "Задачи по уведомлению пользователей о состоянии сервисных работ";
    }

    @Override
    public int initScheduler(SchedulerService schedulerService) {
        PagedRepositoryReader<ServiceWork> reader = new PagedRepositoryReader<>(pageable -> serviceWorkRepository.findAll(new ServiceWorksForNotificationSpecification(), pageable));
        return reader.read(appeals -> {
            appeals.forEach(schedulerService::scheduleServiceWorkNotifications);
            return false;
        });
    }
}
