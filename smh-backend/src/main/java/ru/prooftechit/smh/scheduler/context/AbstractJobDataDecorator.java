package ru.prooftechit.smh.scheduler.context;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.quartz.JobDataMap;

/**
 * @author Roman Zdoronok
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AbstractJobDataDecorator {
    protected final JobDataMap jobDataMap;
}
