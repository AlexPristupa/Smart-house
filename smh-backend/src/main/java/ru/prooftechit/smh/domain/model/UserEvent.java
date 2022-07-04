package ru.prooftechit.smh.domain.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import ru.prooftechit.smh.api.enums.UserEventType;
import ru.prooftechit.smh.domain.model.common.BaseEntity;
import ru.prooftechit.smh.domain.model.metadata.UserEventMetadata;

import javax.persistence.*;
import java.util.Set;

/**
 * Важные события.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "user_event")
@TypeDefs({
    @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class UserEvent extends BaseEntity<UserEvent> {

    /**
     * Тип события
     */
    @Column(name = "type", columnDefinition = "text", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserEventType type;

    @Column(name = "facilityId", columnDefinition = "long", nullable = false)
    private Long facilityId;

    /**
     * Сообщение события
     */
    @Type(type = "jsonb")
    @Column(name = "payload", columnDefinition = "jsonb", nullable = false)
    private String payload;

    /**
     * Метаданные о важных событиях, для каждого пользователя свой экземпляр.
     */
    @OneToMany(mappedBy = "entity", cascade = {CascadeType.REMOVE}, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<UserEventMetadata> metadata;

}