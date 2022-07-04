package ru.prooftechit.smh.event.model.hardware;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.prooftechit.smh.domain.model.Hardware;

/**
 * @author Roman Zdoronok
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class AbstractHardwareEvent {
    private final Hardware hardware;
}
