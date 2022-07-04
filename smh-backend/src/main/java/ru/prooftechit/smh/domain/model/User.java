package ru.prooftechit.smh.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serial;
import java.time.Instant;
import java.util.Date;
import javax.persistence.*;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.api.enums.UserStatus;
import ru.prooftechit.smh.domain.model.common.BaseEntity;

/**
 * @author Roman Zdoronok
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "user")
public class User extends BaseEntity<User> {
    @Serial
    private static final long serialVersionUID = 7552095650418866529L;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "email", columnDefinition = "text", nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Column(name = "password", columnDefinition = "text", nullable = false)
    private String password;

    @Column(name = "first_name", columnDefinition = "text", nullable = false)
    private String firstName;

    @Column(name = "last_name", columnDefinition = "text", nullable = false)
    private String lastName;

    @Column(name = "patronymic", columnDefinition = "text")
    private String patronymic;

    @Column(name = "birth_date")
    private Date birthDate;

    /**
     * Дата последней активности.
     */
    @Column(name = "last_activity_at")
    private Instant lastActivityAt;

    @OneToOne
    @JoinColumn(name = "fk_photo")
    private File photo;
}
