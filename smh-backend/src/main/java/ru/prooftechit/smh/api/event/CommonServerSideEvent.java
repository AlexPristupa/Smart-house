package ru.prooftechit.smh.api.event;

import java.time.Instant;
import lombok.NoArgsConstructor;

/**
 * @author Roman Zdoronok
 */
@NoArgsConstructor
public class CommonServerSideEvent extends AbstractEvent {
    public CommonServerSideEvent(Instant eventDate) {
        super(eventDate);
    }
}
