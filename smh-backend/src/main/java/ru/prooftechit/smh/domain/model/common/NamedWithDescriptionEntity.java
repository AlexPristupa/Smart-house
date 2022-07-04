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
public abstract class NamedWithDescriptionEntity<T extends NamedWithDescriptionEntity<T>> extends BaseEntity<NamedWithDescriptionEntity<T>> {

    @Serial
    private static final long serialVersionUID = -7117613852004336974L;

    @Column(name = "name", columnDefinition = "text", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;


    public T setName(String name) {
        this.name = name;
        return (T) this;
    }

    public T setDescription(String description) {
        this.description = description;
        return (T) this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass() || !super.equals(o)) {
            return false;
        }
        NamedWithDescriptionEntity<?> that = (NamedWithDescriptionEntity<?>) o;
        return Objects.equals(name, that.name) &&
            Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, description);
    }
}
