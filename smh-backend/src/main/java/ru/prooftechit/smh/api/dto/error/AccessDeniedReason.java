package ru.prooftechit.smh.api.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Причина отказа в доступе к ресурсу")
public enum AccessDeniedReason {
    RESTRICTED_ACCESS
}
