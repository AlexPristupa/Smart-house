package ru.prooftechit.smh.domain.util;

import java.io.Serial;
import java.util.List;
import java.util.function.Function;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.prooftechit.smh.api.dto.PageWithUnread;

/**
 * @author Roman Zdoronok
 */

public class PageWithUnreadImpl<T> extends PageImpl<T> implements PageWithUnread<T> {

    @Serial
    private static final long serialVersionUID = 2148691194208099457L;

    private final long totalUnread;

    public PageWithUnreadImpl(List<T> content, Pageable pageable, long total, long totalUnread) {
        super(content, pageable, total);
        this.totalUnread = totalUnread;
    }

    public PageWithUnreadImpl(List<T> content, long totalUnread) {
        super(content);
        this.totalUnread = totalUnread;
    }

    @Override
    public long getTotalUnreadElements() {
        return totalUnread;
    }

    @Override
    public <U> PageWithUnread<U> map(Function<? super T, ? extends U> converter) {
        return new PageWithUnreadImpl<>(getConvertedContent(converter), getPageable(), getTotalElements(), getTotalUnreadElements());
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PageWithUnreadImpl<?>)) {
            return false;
        }

        PageWithUnreadImpl<?> that = (PageWithUnreadImpl<?>) obj;

        return this.totalUnread == that.totalUnread && super.equals(obj);
    }

    @Override
    public int hashCode() {

        int result = 17;

        result += 31 * (int) (totalUnread ^ totalUnread >>> 32);
        result += 31 * super.hashCode();

        return result;
    }
}
