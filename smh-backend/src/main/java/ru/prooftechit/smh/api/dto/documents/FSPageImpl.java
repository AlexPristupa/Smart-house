package ru.prooftechit.smh.api.dto.documents;

import java.io.Serial;
import java.util.List;
import java.util.function.Function;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * @author Roman Zdoronok
 */
public class FSPageImpl<T> extends PageImpl<T> implements FSPage<T> {

    @Serial
    private static final long serialVersionUID = 2496265669448128965L;

    @Getter
    private final DocumentNodeWithParentsDto folder;

    public FSPageImpl(DocumentNodeWithParentsDto folder, Page<T> page) {
        this(folder, page.getContent(), page.getPageable(), page.getTotalElements());
    }

    public FSPageImpl(DocumentNodeWithParentsDto folder, List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
        this.folder = folder;
    }

    public FSPageImpl(DocumentNodeWithParentsDto folder, List<T> content) {
        super(content);
        this.folder = folder;
    }

    @Override
    public <U> FSPage<U> map(Function<? super T, ? extends U> converter) {
        return new FSPageImpl<>(folder, getConvertedContent(converter), getPageable(), getTotalElements());
    }
}
