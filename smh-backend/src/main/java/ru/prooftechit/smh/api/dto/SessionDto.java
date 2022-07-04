package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.Instant;

/**
 * @author Andrey Kovalenko
 */
@Schema(description = "Информация о сессии пользователя")
@Data
@Accessors(chain = true)
public class SessionDto {

    @Schema(example = "2020-09-21T12:12:12", description = "Дата/время начала сессии")
    private Instant createdAt;

    @Schema(example = "Microsoft Windows", description = "Операционная система")
    private String os;

    @Schema(example = "Mozilla Firefox", description = "Браузер")
    private String browser;

    @Schema(example = "190.117.115.150:65103", description = "IP-адрес")
    private String ip;

    @Schema(example = "true", description = "Истекло ли время действия сессии")
    private boolean active;

    @Schema(example = "true", description = "Имеется ли в рамках сессии активные подключения по вебсокету")
    private boolean online;
}
