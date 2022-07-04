package ru.prooftechit.smh.configuration.security;

import java.time.Instant;
import lombok.Builder;
import lombok.Data;

/**
 * Внутренне Dto refresf token-а
 */
@Data
@Builder
public class RefreshToken {

    /**
     * Uuid токена
     */
    String uuid;

    /**
     * Срок действия токена
     */
    Instant expireAt;

    /**
     * Строковое представление токена, передаваемое клиенту
     */
    String token;
}
