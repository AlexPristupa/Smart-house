package ru.prooftechit.smh.domain.model.metadata;

import java.io.Serial;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityMetadataKey implements Serializable {

    @Serial
    private static final long serialVersionUID = -9194337772286530685L;

    @Column(name = "fk_entity", nullable = false)
    private Long entityId;

    @Column(name = "fk_user", nullable = false)
    private Long userId;

}
