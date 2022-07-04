package ru.prooftechit.smh.configuration.mail;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import ru.prooftechit.smh.api.email.EmailTemplate;
import ru.prooftechit.smh.api.notification.AbstractNotification;

/**
 * @author Roman Zdoronok
 */
@Configuration
public class MailConfig {

    @Bean
    public TemplateAggregator getTemplateAggregator(Collection<EmailTemplate<? extends AbstractNotification<?>>> templateContextProvidersList) {
        TemplateAggregator templateAggregator = new TemplateAggregator();
        templateContextProvidersList.forEach(templateAggregator::put);
        return templateAggregator;
    }

    @Bean
    public ResourceBundleMessageSource emailMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/mail-messages");
        return messageSource;
    }

    @Bean
    public TemplateEngine emailTemplateEngine() {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.addTemplateResolver(mailSubjectTemplateResolver());
        templateEngine.addTemplateResolver(mailBodyTemplateResolver());
        templateEngine.addTemplateResolver(customTemplateResolver());
        templateEngine.setTemplateEngineMessageSource(emailMessageSource());
        return templateEngine;
    }

    private ITemplateResolver mailSubjectTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(1);
        templateResolver.setResolvablePatterns(Collections.singleton("subject/*"));
        templateResolver.setPrefix("/templates/mail/");
        templateResolver.setSuffix(".txt");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    private ITemplateResolver mailBodyTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(2);
        templateResolver.setResolvablePatterns(Collections.singleton("body/*"));
        templateResolver.setPrefix("/templates/mail/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheable(false);
        return templateResolver;
    }

    private ITemplateResolver customTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(3);
        templateResolver.setResolvablePatterns(Collections.singleton("custom/*"));
        templateResolver.setPrefix("/templates/mail/");
        templateResolver.setSuffix(".txt");
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheable(false);
        return templateResolver;
    }


}
