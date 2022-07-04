package ru.prooftechit.smh.scheduler.hardware;

import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import ru.prooftechit.smh.api.service.SchedulerService;
import ru.prooftechit.smh.domain.model.Hardware;
import ru.prooftechit.smh.domain.repository.HardwareRepository;
import ru.prooftechit.smh.domain.util.PagedRepositoryReader;
import ru.prooftechit.smh.scheduler.ComponentSchedulerInitializer;

@Order(2)
@Component
@AllArgsConstructor
public class HardwareInitializer implements ComponentSchedulerInitializer {
    private final HardwareRepository hardwareRepository;

    @Override
    public String getComponentName() {
        return "Задачи по уведомлению пользователей о состоянии оборудования";
    }

    @Override
    public int initScheduler(SchedulerService schedulerService) {
        PagedRepositoryReader<Hardware> reader = new PagedRepositoryReader<>(pageable -> hardwareRepository.findAll(pageable));
        return reader.read(appeals -> {
            appeals.forEach(schedulerService::scheduleHardwareNotifications);
            return false;
        });
    }
}
