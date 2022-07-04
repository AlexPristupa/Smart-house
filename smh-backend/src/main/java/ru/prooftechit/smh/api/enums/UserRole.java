package ru.prooftechit.smh.api.enums;

import io.swagger.annotations.ApiModel;

/**
 * @author Roman Zdoronok
 */
@ApiModel(description = "Роли пользователя")
public enum UserRole {
    READER,
    WRITER,
    ADMIN,
    ROOT
}
