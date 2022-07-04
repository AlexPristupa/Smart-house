package ru.prooftechit.smh.domain.model;

import java.io.Serial;
import java.time.Instant;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import ru.prooftechit.smh.domain.model.common.NamedWithDescriptionEntity;

/**
 * @author Roman Zdoronok
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Entity
@Table(name = "hardware")
public class Hardware extends NamedWithDescriptionEntity<Hardware> {

    @Serial
    private static final long serialVersionUID = 4913289170162888636L;

    @ManyToOne
    @JoinColumn(name = "fk_photo")
    private File photo;

    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(
            name = "hardware_images",
            joinColumns = @JoinColumn(name = "fk_hardware"),
            inverseJoinColumns = @JoinColumn(name = "fk_image")
    )
    @OrderBy("createdAt")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<File> images;

    @Column(name = "model", columnDefinition = "text", nullable = false)
    private String model;

    @Column(name = "serial_number", columnDefinition = "text", nullable = false)
    private String serialNumber;

    @Column(name = "installed_at", nullable = false)
    private Instant installedAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "installer", columnDefinition = "text")
    private String installer;

    @ManyToOne
    @JoinColumn(name = "fk_facility", nullable = false)
    private Facility facility;
}
