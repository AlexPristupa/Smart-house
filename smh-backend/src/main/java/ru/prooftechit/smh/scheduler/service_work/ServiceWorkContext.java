package ru.prooftechit.smh.scheduler.service_work;

import java.util.Map;
import org.quartz.JobDataMap;
import ru.prooftechit.smh.domain.model.ServiceWork;
import ru.prooftechit.smh.scheduler.context.AbstractJobDataDecorator;

/**
 * @author Roman Zdoronok
 */
public class ServiceWorkContext extends AbstractJobDataDecorator {
    private static final String PROP_SERVICE_WORK_ID = "service_work_id";

    public ServiceWorkContext(JobDataMap jobDataMap) {
        super(jobDataMap);
    }

    public ServiceWorkContext(ServiceWork serviceWork) {
        super(new JobDataMap(Map.of(PROP_SERVICE_WORK_ID, serviceWork.getId())));
    }

    public long getServiceWorkId() {
        return jobDataMap.getLong(PROP_SERVICE_WORK_ID);
    }
}
