package ru.prooftechit.smh.api.enums;

import io.swagger.annotations.ApiModel;

@ApiModel(description = "Типы важных событий")
public enum UserEventType {
    HARDWARE_EXPIRED,
    SERVICE_WORK_BEFORE_START,
    SERVICE_WORK_FINISHED,
}
