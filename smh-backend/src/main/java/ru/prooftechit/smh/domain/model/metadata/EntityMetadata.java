package ru.prooftechit.smh.domain.model.metadata;

import java.io.Serial;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.common.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;

@MappedSuperclass
@Getter
@Setter
@Accessors(chain = true)
public class EntityMetadata<E extends BaseEntity<?>> implements Serializable {

    @Serial
    private static final long serialVersionUID = -6142030106117030822L;

    @EmbeddedId
    private EntityMetadataKey id;

    @ManyToOne
    @JoinColumn(name = "fk_entity", insertable = false, updatable = false)
    private E entity;

    @ManyToOne
    @JoinColumn(name = "fk_user", insertable = false, updatable = false)
    private User user;

    @Column(name = "read", nullable = false)
    private boolean read;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;
}
