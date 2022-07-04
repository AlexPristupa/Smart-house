package ru.prooftechit.smh.api.enums;

import io.swagger.annotations.ApiModel;
import java.util.Set;

/**
 * @author Roman Zdoronok
 */
@ApiModel(description = "Статусы пользователя")
public enum UserStatus {
    NEW,
    UNAPPROVED,
    ACTIVE,
    ARCHIVED,
    BLOCKED,
    DELETED,
}