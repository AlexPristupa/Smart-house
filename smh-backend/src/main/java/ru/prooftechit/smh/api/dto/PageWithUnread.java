package ru.prooftechit.smh.api.dto;

import java.util.function.Function;
import org.springframework.data.domain.Page;

/**
 * @author Roman Zdoronok
 */
public interface PageWithUnread<T> extends Page<T> {
    <U> PageWithUnread<U> map(Function<? super T, ? extends U> converter);
    long getTotalUnreadElements();
}
