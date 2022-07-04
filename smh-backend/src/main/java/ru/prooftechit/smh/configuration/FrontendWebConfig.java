package ru.prooftechit.smh.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.ConverterRegistry;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.prooftechit.smh.configuration.converter.StringToEnumIgnoreCaseConverterFactory;

/**
 * @author Roman Zdoronok
 */
@Configuration
public class FrontendWebConfig implements WebMvcConfigurer {
    /**
     * Ensure client-side paths redirect to index.html because client handles routing.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Map "/"
        registry.addViewController("/")
            .setViewName("forward:/index.html");

        // Map "/word", "/word/word", and "/word/word/word" - except for anything starting with "/api/..." or ending with
        // a file extension like ".js" - to index.html. By doing this, the client receives and routes the url. It also
        // allows client-side URLs to be bookmarked.

        // Single directory level - no need to exclude "api"
        registry.addViewController("/{x:[\\w\\-]+}")
            .setViewName("forward:/index.html");
        // Multi-level directory path, need to exclude "api" on the first part of the path
        registry.addViewController("/{x:^(?!api$).*$}/**/{y:[\\w\\-]+}")
            .setViewName("forward:/index.html");
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumIgnoreCaseConverterFactory());
    }

}
