package ru.prooftechit.smh.api.enums;

import io.swagger.annotations.ApiModel;

/**
 * @author Roman Zdoronok
 */
@ApiModel(description = "Варианты статуса сервисной работы")
public enum ServiceWorkStatus {
    PENDING,
    IN_PROGRESS,
    FINISHED
}
