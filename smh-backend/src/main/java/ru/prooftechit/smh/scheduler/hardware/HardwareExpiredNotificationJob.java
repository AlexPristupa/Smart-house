package ru.prooftechit.smh.scheduler.hardware;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ru.prooftechit.smh.api.dto.HardwareDto;
import ru.prooftechit.smh.api.notification.NotificationManager;
import ru.prooftechit.smh.domain.model.Hardware;
import ru.prooftechit.smh.domain.repository.HardwareRepository;
import ru.prooftechit.smh.mapper.HardwareMapper;
import ru.prooftechit.smh.notification.model.HardwareExpiredNotification;

@Slf4j
@RequiredArgsConstructor
public class HardwareExpiredNotificationJob implements Job {

    private final NotificationManager notificationManager;
    private final HardwareRepository hardwareRepository;
    private final HardwareMapper hardwareMapper;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        HardwareContext hardwareContext = new HardwareContext(context.getJobDetail().getJobDataMap());
        Hardware hardware = hardwareRepository.findById(hardwareContext.getHardwareId()).orElse(null);
        HardwareDto hardwareDto = hardwareMapper.toDto(hardware);
        notificationManager.push(new HardwareExpiredNotification(
                context.getFireTime().toInstant(),
                hardwareDto
        ));
    }
}
