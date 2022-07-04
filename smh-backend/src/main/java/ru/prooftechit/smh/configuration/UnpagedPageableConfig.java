package ru.prooftechit.smh.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

/**
 * @author Roman Zdoronok
 */
@Configuration
public class UnpagedPageableConfig implements PageableHandlerMethodArgumentResolverCustomizer {

    @Override
    public void customize(PageableHandlerMethodArgumentResolver pageableHandlerMethodArgumentResolver) {
        pageableHandlerMethodArgumentResolver.setFallbackPageable(Pageable.unpaged());
    }
}
