package ru.prooftechit.smh.domain.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import ru.prooftechit.smh.api.dto.documents.NodeType;
import ru.prooftechit.smh.domain.model.common.BaseEntity;

/**
 * Файл.
 *
 * @author Yan Yukhnovets
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@Accessors(chain = true)
@Entity
@Table(name = "file_system_node")
public class FileSystemNode extends BaseEntity<FileSystemNode> {

    @Column(name = "name", columnDefinition = "text")
    private String name;

    @Column(name = "node_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private NodeType nodeType;

    @OneToOne
    @JoinColumn(name = "fk_file")
    private File file;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_parent")
    private FileSystemNode parent;

    @ManyToOne
    @JoinColumn(name = "fk_facility", nullable = false)
    private Facility facility;
}
