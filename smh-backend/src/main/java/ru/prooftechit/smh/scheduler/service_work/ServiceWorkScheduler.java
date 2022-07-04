package ru.prooftechit.smh.scheduler.service_work;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.api.enums.ServiceWorkStatus;
import ru.prooftechit.smh.configuration.properties.SchedulingProperties;
import ru.prooftechit.smh.domain.model.ServiceWork;
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
public class ServiceWorkScheduler extends AbstractScheduler {

    private static final String JOB_GROUP = "service_work_notification_group";
    private static final String JOB_PLANNED_NAME = "service_work_planned_notification_[%d]";
    private static final String JOB_BEFORE_START_NAME = "service_work_before_start_notification_[%d]";
    private static final String JOB_STARTED_NAME = "service_work_started_[%d]";
    private static final String JOB_FINISHED_NAME = "service_work_finished_[%d]";

    public ServiceWorkScheduler(Scheduler scheduler, SchedulingProperties schedulingProperties) {
        super(scheduler, schedulingProperties);
    }

    public void schedule(ServiceWork serviceWork) {
        Duration beforeStartDuration = schedulingProperties.getServiceWorksNotification().getBeforeStartDuration();
        Instant fireDate = serviceWork.getStartTime().minus(beforeStartDuration);
        ServiceWorkContext serviceWorkContext = new ServiceWorkContext(serviceWork);

        JobDetail jobStartedDetail = getStartedNotificationDetail(serviceWorkContext);
        Trigger jobStartedNotificationTrigger = getTrigger(Date.from(Instant.now()));

        JobDetail jobBeforeStartDetail = getBeforeStartNotificationDetail(serviceWorkContext);
        Trigger jobBeforeStartNotificationTrigger = getTrigger(Date.from(fireDate));

        JobDetail jobFinishedDetail = getFinishedNotificationDetail(serviceWorkContext);
        Trigger jobFinishedNotificationTrigger = getTrigger(Date.from(serviceWork.getFinishTime()));

        try {
            cancel(serviceWork.getId());
            Instant now = Instant.now();
            if (serviceWork.getStatus().equals(ServiceWorkStatus.PENDING)) {
                scheduler.scheduleJob(jobStartedDetail, jobStartedNotificationTrigger);
                log.info("Добавлено задание о начале сервисной работы [{}]", serviceWork.getId());
            }
            if (fireDate.isAfter(now) && serviceWork.getStartTime().isAfter(now)) {
                scheduler.scheduleJob(jobBeforeStartDetail, jobBeforeStartNotificationTrigger);
                log.info("Добавлено задание о плановом уведомлении перед началом работы [{}]", serviceWork.getId());
            }
            if (serviceWork.getFinishTime().isAfter(now)) {
                scheduler.scheduleJob(jobFinishedDetail, jobFinishedNotificationTrigger);
                log.info("Добавлено задание о завершении сервисной работы [{}]", serviceWork.getId());
            }
        } catch (SchedulerException e) {
            log.error("Ошибка при выполнении уведомления о сервисной работы", e);
        }
    }

    public void cancel(Long serviceWorkId) {
        try {
            log.info("Отмена заданий по уведомлению о статусе сервисной работы  [{}]", serviceWorkId);
            scheduler.deleteJobs(Arrays.asList(getPlannedJobKey(serviceWorkId),
                    getBeforeStartJobKey(serviceWorkId),
                    getStartedJobKey(serviceWorkId),
                    getFinishedJobKey(serviceWorkId)));
        } catch (SchedulerException e) {
            log.error("Возникла ошибка при отмене задач по уведомлении о статусе сервисной работы.", e);
        }
    }

    private static JobKey getPlannedJobKey(Long serviceWorkId) {
        return JobKey.jobKey(String.format(JOB_PLANNED_NAME, serviceWorkId), JOB_GROUP);
    }

    private static JobKey getBeforeStartJobKey(Long serviceWorkId) {
        return JobKey.jobKey(String.format(JOB_BEFORE_START_NAME, serviceWorkId), JOB_GROUP);
    }

    private static JobKey getStartedJobKey(Long serviceWorkId) {
        return JobKey.jobKey(String.format(JOB_STARTED_NAME, serviceWorkId), JOB_GROUP);
    }

    private static JobKey getFinishedJobKey(Long serviceWorkId) {
        return JobKey.jobKey(String.format(JOB_FINISHED_NAME, serviceWorkId), JOB_GROUP);
    }

    private JobDetail getStartedNotificationDetail(ServiceWorkContext context) {
        return JobBuilder.newJob()
                .ofType(ServiceWorkStartedJob.class)
                .withIdentity(getStartedJobKey(context.getServiceWorkId()))
                .storeDurably(false)
                .setJobData(context.getJobDataMap())
                .build();
    }

    private JobDetail getBeforeStartNotificationDetail(ServiceWorkContext context) {
        return JobBuilder.newJob()
                .ofType(ServiceWorkBeforeStartJob.class)
                .withIdentity(getBeforeStartJobKey(context.getServiceWorkId()))
                .storeDurably(false)
                .setJobData(context.getJobDataMap())
                .build();
    }

    private JobDetail getFinishedNotificationDetail(ServiceWorkContext context) {
        return JobBuilder.newJob()
                .ofType(ServiceWorkFinishedJob.class)
                .withIdentity(getFinishedJobKey(context.getServiceWorkId()))
                .storeDurably(false)
                .setJobData(context.getJobDataMap())
                .build();
    }
}
