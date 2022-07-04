package ru.prooftechit.smh.domain.model;

import java.io.Serial;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.content.commons.annotations.ContentId;
import org.springframework.content.commons.annotations.ContentLength;
import org.springframework.content.commons.annotations.MimeType;
import ru.prooftechit.smh.domain.model.common.BaseEntity;

import javax.persistence.*;
import java.util.UUID;

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
@Table(name = "file")
public class File extends BaseEntity<File> {

    @Serial
    private static final long serialVersionUID = -2501020378396132106L;

    @Column(name = "name", columnDefinition = "text")
    private String name;

    @ContentId
    @Column(name = "content_id", unique = true)
    private UUID contentId;

    @ContentLength
    @Column(name = "content_length")
    private Long contentLength;

    @MimeType
    @Column(name = "mime_type", columnDefinition = "text")
    private String mimeType;

    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_preview")
    private File preview;
}
