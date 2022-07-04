package ru.prooftechit.smh.event.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.prooftechit.smh.api.enums.UserEventType;
import ru.prooftechit.smh.api.service.SchedulerService;
import ru.prooftechit.smh.api.service.UserEventService;
import ru.prooftechit.smh.domain.model.Hardware;
import ru.prooftechit.smh.event.model.hardware.HardwareChangedEvent;
import ru.prooftechit.smh.event.model.hardware.HardwareCreatedEvent;
import ru.prooftechit.smh.mapper.HardwareMapper;

@Component
@Slf4j
@RequiredArgsConstructor
public class HardwareEventsHandler {
    private final HardwareMapper hardwareMapper;
    private final SchedulerService schedulerService;
    private final UserEventService userEventService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleNewHardwareEvent(HardwareCreatedEvent event) {
        Hardware hardware = event.getHardware();
        schedulerService.scheduleHardwareNotifications(hardware);
        userEventService.create(hardwareMapper.toDto(hardware), UserEventType.HARDWARE_EXPIRED, hardware.getFacility().getId());
        log.info("Создана новая запись об оборудовании [{}]", hardware.getId());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleUpdateHardwareEvent(HardwareChangedEvent event) {
        Hardware hardware = event.getHardware();
        schedulerService.scheduleHardwareNotifications(hardware);
        log.info("Обновлена запись об оборудовании [{}]", hardware.getId());
    }
}
