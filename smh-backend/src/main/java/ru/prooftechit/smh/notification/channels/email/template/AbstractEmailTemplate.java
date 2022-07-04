package ru.prooftechit.smh.notification.channels.email.template;

import java.util.Calendar;
import java.util.Locale;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.prooftechit.smh.api.email.Email;
import ru.prooftechit.smh.api.email.EmailTemplate;
import ru.prooftechit.smh.api.notification.AbstractNotification;
import ru.prooftechit.smh.configuration.properties.FrontendProperties;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractEmailTemplate<N extends AbstractNotification<?>> implements EmailTemplate<N> {

    private static final Locale LOCALE_RU = new Locale("ru", "RU");

    private final TemplateEngine emailTemplateEngine;
    protected final FrontendProperties frontendProperties;

    protected abstract void enrichContext(Context context, N notification);
    protected abstract String getBodyTemplateName();
    protected abstract String getSubjectTemplateName();

    @Override
    public Email process(N notification, User target) {
        Context context = new Context(LOCALE_RU);
        context.setVariable("year", String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
        context.setVariable("payload", notification.getPayload());
        context.setVariable("target", target);
        enrichContext(context, notification);
        final String subject = emailTemplateEngine.process(getSubjectTemplateName(), context);
        final String htmlContent = emailTemplateEngine.process(getBodyTemplateName(), context);
        return new Email(subject, htmlContent);
    }
}
