package ru.prooftechit.smh.api.dto.documents;

import java.util.function.Function;
import org.springframework.data.domain.Page;

/**
 * @author Roman Zdoronok
 */
public interface FSPage<T> extends Page<T> {
    DocumentNodeWithParentsDto getFolder();

    <U> FSPage<U> map(Function<? super T, ? extends U> converter);
}
