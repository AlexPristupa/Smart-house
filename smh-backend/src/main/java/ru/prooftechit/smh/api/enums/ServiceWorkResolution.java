package ru.prooftechit.smh.api.enums;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Варианты завершения сервисной работы")
public enum ServiceWorkResolution {
    RESOLVED,
    UNRESOLVED
}
