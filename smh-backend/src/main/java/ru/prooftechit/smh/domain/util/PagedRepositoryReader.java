package ru.prooftechit.smh.domain.util;

import java.util.function.Function;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * @author Roman Zdoronok
 */
public class PagedRepositoryReader<E> {
    private static final int DEFAULT_PAGE_SIZE = 1000;

    private final Function<Pageable, Page<E>> producerFunction;
    private final Sort sort;
    private final int pageSize;
    private final boolean deleteMode;

    public PagedRepositoryReader(Function<Pageable, Page<E>> producerFunction) {
        this(producerFunction, DEFAULT_PAGE_SIZE);
    }

    public PagedRepositoryReader(Function<Pageable, Page<E>> producerFunction, boolean deleteMode) {
        this(producerFunction, DEFAULT_PAGE_SIZE, deleteMode);
    }

    public PagedRepositoryReader(Function<Pageable, Page<E>> producerFunction, Sort sort) {
        this(producerFunction, sort, DEFAULT_PAGE_SIZE);
    }

    public PagedRepositoryReader(Function<Pageable, Page<E>> producerFunction, Sort sort, boolean deleteMode) {
        this(producerFunction, sort, DEFAULT_PAGE_SIZE, deleteMode);
    }

    public PagedRepositoryReader(Function<Pageable, Page<E>> producerFunction, int pageSize) {
        this(producerFunction, Sort.unsorted(), pageSize);
    }

    public PagedRepositoryReader(Function<Pageable, Page<E>> producerFunction, int pageSize, boolean deleteMode) {
        this(producerFunction, Sort.unsorted(), pageSize, deleteMode);
    }

    public PagedRepositoryReader(Function<Pageable, Page<E>> producerFunction,
                                 Sort sort,
                                 int pageSize)
    {
        this(producerFunction, sort, pageSize, false);
    }
    
    public PagedRepositoryReader(Function<Pageable, Page<E>> producerFunction,
                                 Sort sort,
                                 int pageSize,
                                 boolean deleteMode) {
        this.producerFunction = producerFunction;
        this.sort = sort;
        this.pageSize = pageSize;
        this.deleteMode = deleteMode;
    }

    public int read(Function<Page<E>, Boolean> consumerFunction) {
        // Пока нет "защиты от дурака", валидность размера страницы не проверяется.
        Pageable pageRequest = PageRequest.of(0, pageSize, sort);
        int processed = 0;
        Page<E> page;
        boolean stopNow;
        boolean nextPageAvailable;
        do {
            nextPageAvailable = false;
            page = producerFunction.apply(pageRequest);
            stopNow = consumerFunction.apply(page);
            processed += page.getNumberOfElements();
            if (!stopNow && page.hasNext()) {
                if (!deleteMode) {
                    pageRequest = page.nextPageable();
                }
                nextPageAvailable = pageRequest.isPaged();
            }
        } while (!stopNow && nextPageAvailable && !Thread.currentThread().isInterrupted());

        return processed;
    }
}
