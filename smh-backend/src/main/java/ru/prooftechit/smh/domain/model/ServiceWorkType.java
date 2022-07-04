package ru.prooftechit.smh.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.domain.model.common.NamedWithDescriptionEntity;

/**
 * @author Roman Zdoronok
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "service_work_type")
public class ServiceWorkType extends NamedWithDescriptionEntity<ServiceWorkType> {

    @Column(name = "icon", columnDefinition = "text")
    private String icon;

    @Column(name = "pinned", columnDefinition = "boolean", nullable = false)
    private boolean pinned;

}
