package ru.prooftechit.smh.scheduler;

import java.util.Date;
import lombok.AllArgsConstructor;
import org.quartz.Scheduler;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import ru.prooftechit.smh.configuration.properties.SchedulingProperties;

/**
 * @author Andrey Kovalenko
 */
@AllArgsConstructor
public abstract class AbstractScheduler {

    protected Scheduler scheduler;
    protected SchedulingProperties schedulingProperties;

    protected Trigger getTrigger(Date date) {
        return TriggerBuilder.newTrigger()
                             .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                                                .withMisfireHandlingInstructionFireNow())
                             .startAt(date)
                             .build();
    }

    protected Trigger getTrigger(Date date, TriggerKey triggerKey) {
        return TriggerBuilder.newTrigger()
                             .withIdentity(triggerKey)
                             .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                                                                .withMisfireHandlingInstructionFireNow())
                             .startAt(date)
                             .build();
    }
}
