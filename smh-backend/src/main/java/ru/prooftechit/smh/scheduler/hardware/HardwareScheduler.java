package ru.prooftechit.smh.scheduler.hardware;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.configuration.properties.SchedulingProperties;
import ru.prooftechit.smh.domain.model.Hardware;
import ru.prooftechit.smh.scheduler.AbstractScheduler;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;

/**
 * @author Roman Zdoronok
 */
@Service
@Slf4j
public class HardwareScheduler extends AbstractScheduler {

    private static final String JOB_GROUP = "hardware_notification_group";
    private static final String JOB_EXPIRED = "hardware_expired_notification_[%d]";

    public HardwareScheduler(Scheduler scheduler, SchedulingProperties schedulingProperties) {
        super(scheduler, schedulingProperties);
    }

    public void schedule(Hardware hardware) {
        Duration beforeStartDuration = schedulingProperties.getHardwareNotification().getBeforeStartDuration();
        Instant fireDate = hardware.getExpiresAt().minus(beforeStartDuration);
        HardwareContext hardwareContext = new HardwareContext(hardware);

        JobDetail jobStartedDetail = getExpiredNotificationDetail(hardwareContext);
        Trigger jobStartedNotificationTrigger = getTrigger(Date.from(fireDate));

        try {
            cancel(hardware.getId());
            Instant now = Instant.now();
            if (fireDate.isAfter(now) && hardware.getExpiresAt().isAfter(now)) {
                scheduler.scheduleJob(jobStartedDetail, jobStartedNotificationTrigger);
                log.info("Добавлено задание на уведомление о проверке оборудования [{}]", hardware.getId());
            }
        } catch (SchedulerException e) {
            log.error("Ошибка при выполнении уведомления о сроке проверки оборудования", e);
        }
    }

    public void cancel(Long hardwareId) {
        try {
            log.info("Отмена заданий по уведомлению о проверке оборудования  [{}]", hardwareId);
            scheduler.deleteJobs(Arrays.asList(getExpiredJobKey(hardwareId)));
        } catch (SchedulerException e) {
            log.error("Возникла ошибка при отмене задач о проверке оборудования.", e);
        }
    }

    private static JobKey getExpiredJobKey(Long hardwareId) {
        return JobKey.jobKey(String.format(JOB_EXPIRED, hardwareId), JOB_GROUP);
    }

    private JobDetail getExpiredNotificationDetail(HardwareContext context) {
        return JobBuilder.newJob()
                .ofType(HardwareExpiredNotificationJob.class)
                .withIdentity(getExpiredJobKey(context.getHardwareId()))
                .storeDurably(false)
                .setJobData(context.getJobDataMap())
                .build();
    }
}
