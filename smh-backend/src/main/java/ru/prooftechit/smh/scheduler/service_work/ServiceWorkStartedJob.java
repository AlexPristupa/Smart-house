package ru.prooftechit.smh.scheduler.service_work;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import ru.prooftechit.smh.api.service.ServiceWorkService;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@RequiredArgsConstructor
public class ServiceWorkStartedJob implements Job {

    private final ServiceWorkService serviceWorkService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        ServiceWorkContext serviceWorkContext = new ServiceWorkContext(context.getJobDetail().getJobDataMap());
        serviceWorkService.startServiceWork(serviceWorkContext.getServiceWorkId());
    }
}
