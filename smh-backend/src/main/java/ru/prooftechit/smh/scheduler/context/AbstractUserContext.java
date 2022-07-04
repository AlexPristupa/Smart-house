package ru.prooftechit.smh.scheduler.context;

import java.util.Map;
import org.quartz.JobDataMap;

/**
 * @author Roman Zdoronok
 */
public abstract class AbstractUserContext extends AbstractJobDataDecorator {
    private static final String PROP_USER_ID = "user_id";

    protected AbstractUserContext(JobDataMap jobDataMap) {
        super(jobDataMap);
    }

    protected AbstractUserContext(Long userId) {
        super(new JobDataMap(Map.of(PROP_USER_ID, userId)));
    }

    public Long getUserId() {
        return jobDataMap.getLong(PROP_USER_ID);
    }
}
