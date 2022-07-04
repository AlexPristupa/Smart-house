package ru.prooftechit.smh.domain.model;

import java.io.Serial;
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
@Table(name = "facility")
public class Facility extends NamedWithDescriptionEntity<Facility> {

    @Serial
    private static final long serialVersionUID = 8235862114667522403L;

    @Column(name = "address", columnDefinition = "text", nullable = false)
    private String address;

    @ManyToOne
    @JoinColumn(name = "fk_photo")
    private File photo;

    @OneToMany(cascade = {CascadeType.REMOVE}, fetch = FetchType.EAGER)
    @JoinTable(
        name = "facility_images",
        joinColumns = @JoinColumn(name = "fk_facility"),
        inverseJoinColumns = @JoinColumn(name = "fk_image")
    )
    @OrderBy("createdAt")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<File> images;

}
