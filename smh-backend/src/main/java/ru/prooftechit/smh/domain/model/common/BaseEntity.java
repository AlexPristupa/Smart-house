package ru.prooftechit.smh.domain.model.common;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * Base entity.
 *
 * @author Yan Yukhnovets
 */
@Setter
@Getter
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public abstract class BaseEntity<T extends BaseEntity<T>> implements Serializable {

    @Serial
    private static final long serialVersionUID = -6437586311442901415L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "bigserial")
    private Long id;

    /**
     * Дата создания записи.
     */
    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private Instant createdAt;

    /**
     * Дата обновления записи.
     */
    @UpdateTimestamp
    @Column(name = "updated_at")
    private Instant updatedAt;

    public BaseEntity(Long id) {
        this.id = id;
    }

    public T setId(Long id) {
        this.id = id;
        return (T) this;
    }

    public T setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
        return (T) this;
    }

    public T setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
        return (T) this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BaseEntity<?> that = (BaseEntity<?>) o;
        return !Objects.isNull(id) && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
