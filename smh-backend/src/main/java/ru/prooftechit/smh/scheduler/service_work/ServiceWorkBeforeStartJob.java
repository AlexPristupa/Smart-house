package ru.prooftechit.smh.scheduler.service_work;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ru.prooftechit.smh.api.dto.ServiceWorkDto;
import ru.prooftechit.smh.api.dto.notification.ServiceWorkNotificationDto;
import ru.prooftechit.smh.api.notification.NotificationManager;
import ru.prooftechit.smh.api.service.ServiceWorkService;
import ru.prooftechit.smh.domain.model.ServiceWork;
import ru.prooftechit.smh.domain.repository.ServiceWorkRepository;
import ru.prooftechit.smh.mapper.FacilityMapper;
import ru.prooftechit.smh.notification.model.ServiceWorkBeforeStartNotification;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@RequiredArgsConstructor
public class ServiceWorkBeforeStartJob implements Job {

    private final FacilityMapper facilityMapper;
    private final NotificationManager notificationManager;
    private final ServiceWorkRepository serviceWorkRepository;
    private final ServiceWorkService serviceWorkService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ServiceWorkContext serviceWorkContext = new ServiceWorkContext(context.getJobDetail().getJobDataMap());
        ServiceWorkDto serviceWorkDto = serviceWorkService.getServiceWork(serviceWorkContext.getServiceWorkId());
        ServiceWork serviceWork = serviceWorkRepository.findById(serviceWorkContext.getServiceWorkId()).orElseThrow();

        ServiceWorkNotificationDto serviceWorkNotificationDto = new ServiceWorkNotificationDto(
                serviceWorkDto,
                facilityMapper.toDto(serviceWork.getFacility())
        );
        notificationManager.push(new ServiceWorkBeforeStartNotification(
                context.getFireTime().toInstant(),
                serviceWorkNotificationDto
        ));
    }
}
