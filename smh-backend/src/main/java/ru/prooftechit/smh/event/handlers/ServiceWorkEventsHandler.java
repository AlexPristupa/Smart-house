package ru.prooftechit.smh.event.handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import ru.prooftechit.smh.api.dto.notification.ServiceWorkNotificationDto;
import ru.prooftechit.smh.api.enums.UserEventType;
import ru.prooftechit.smh.api.notification.NotificationManager;
import ru.prooftechit.smh.api.service.SchedulerService;
import ru.prooftechit.smh.api.service.UserEventService;
import ru.prooftechit.smh.domain.model.ServiceWork;
import ru.prooftechit.smh.event.model.service_work.ServiceWorkFinishedEvent;
import ru.prooftechit.smh.event.model.service_work.ServiceWorkPlannedEvent;
import ru.prooftechit.smh.mapper.FacilityMapper;
import ru.prooftechit.smh.mapper.ServiceWorkMapper;
import ru.prooftechit.smh.notification.model.ServiceWorkBeforeStartNotification;
import ru.prooftechit.smh.notification.model.ServiceWorkFinishedNotification;

import java.time.Instant;

/**
 * @author Roman Zdoronok
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class ServiceWorkEventsHandler {
    private final FacilityMapper facilityMapper;
    private final NotificationManager notificationManager;
    private final SchedulerService schedulerService;
    private final ServiceWorkMapper serviceWorkMapper;
    private final UserEventService userEventService;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleNewServiceWorkEvent(ServiceWorkPlannedEvent event) {
        ServiceWork serviceWork = event.getServiceWork();
        schedulerService.scheduleServiceWorkNotifications(serviceWork);
        userEventService.create(serviceWorkMapper.toDto(serviceWork),
            UserEventType.SERVICE_WORK_BEFORE_START,
            serviceWork.getFacility().getId());
        log.info("Создана новая сервисная работа [{}]", serviceWork.getId());
    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleFinishedServiceWorkEvent(ServiceWorkFinishedEvent event) {
        ServiceWork serviceWork = event.getServiceWork();
        ServiceWorkNotificationDto serviceWorkNotificationDto = new ServiceWorkNotificationDto(
                serviceWorkMapper.toDto(serviceWork),
                facilityMapper.toDto(serviceWork.getFacility())
        );
        userEventService.create(serviceWorkMapper.toDto(serviceWork),
                UserEventType.SERVICE_WORK_FINISHED,
                serviceWork.getFacility().getId());
        notificationManager.push(new ServiceWorkFinishedNotification(
                event,
                serviceWorkNotificationDto
        ));
        log.info("Завершена сервисная работа [{}]", serviceWork.getId());
    }
}
