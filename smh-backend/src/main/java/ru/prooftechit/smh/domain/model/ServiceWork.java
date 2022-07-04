package ru.prooftechit.smh.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.api.enums.ServiceWorkResolution;
import ru.prooftechit.smh.api.enums.ServiceWorkStatus;
import ru.prooftechit.smh.domain.model.common.NamedWithDescriptionEntity;

import java.time.Instant;

/**
 * @author Roman Zdoronok
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "service_work")
public class ServiceWork extends NamedWithDescriptionEntity<ServiceWork> {

    @Column(name = "status", columnDefinition = "text", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceWorkStatus status = ServiceWorkStatus.PENDING;

    @Column(name = "resolution", columnDefinition = "text", nullable = false)
    @Enumerated(EnumType.STRING)
    private ServiceWorkResolution resolution = ServiceWorkResolution.UNRESOLVED;

    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "finish_time", nullable = false)
    private Instant finishTime;

    @Column(name = "resolution_time")
    private Instant resolutionTime;

    @ManyToOne
    @JoinColumn(name = "fk_type")
    private ServiceWorkType type;

    @ManyToOne
    @JoinColumn(name = "fk_facility", nullable = false)
    private Facility facility;
}
