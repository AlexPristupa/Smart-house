package ru.prooftechit.smh.domain.model;

import lombok.*;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.domain.model.common.BaseEntity;

import javax.persistence.*;
import java.time.Instant;

/**
 * Сессия пользователя.
 *
 * @author Andrey Kovalenko
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "session")
public class Session extends BaseEntity<Session> {

    private static final long serialVersionUID = 2767626338058053579L;

    /**
     * Ссылка на сущность "Пользователя".
     */
    @ManyToOne
    @JoinColumn(name = "fk_user", nullable = false)
    private User user;

    /**
     * uuid refresh token
     */
    @Column(name = "token_uuid", columnDefinition = "text", nullable = false)
    private String tokenUuid;

    /**
     * Срок окончания действия сессии (= сроку окончания действия refresh token)
     */
    @Column(name = "expire_at")
    private Instant expireAt;

    /**
     * Операционная система.
     */
    @Column(name = "os", columnDefinition = "text")
    private String os;

    /**
     * Браузер.
     */
    @Column(name = "browser", columnDefinition = "text")
    private String browser;

    /**
     * IP.
     */
    @Column(name = "ip", columnDefinition = "text")
    private String ip;

    /**
     * Имеется ли в рамках сессии активные подключения по вебсокету
     */
    @Column(name = "online")
    private boolean online;
}
