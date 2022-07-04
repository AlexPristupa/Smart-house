package ru.prooftechit.smh.scheduler.hardware;

import java.util.Map;
import org.quartz.JobDataMap;
import ru.prooftechit.smh.domain.model.Hardware;
import ru.prooftechit.smh.scheduler.context.AbstractJobDataDecorator;

/**
 * @author Roman Zdoronok
 */
public class HardwareContext extends AbstractJobDataDecorator {
    private static final String PROP_HARDWARE_ID = "hardware_id";

    public HardwareContext(JobDataMap jobDataMap) {
        super(jobDataMap);
    }

    public HardwareContext(Hardware hardware) {
        super(new JobDataMap(Map.of(PROP_HARDWARE_ID, hardware.getId())));
    }

    public long getHardwareId() {
        return jobDataMap.getLong(PROP_HARDWARE_ID);
    }
}
