package ru.prooftechit.smh.notification.channels.email.template;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.prooftechit.smh.api.dto.HardwareDto;
import ru.prooftechit.smh.configuration.properties.FrontendProperties;
import ru.prooftechit.smh.notification.model.HardwareExpiredNotification;

import java.util.Date;

@Component
public class HardwareExpiredTemplate extends AbstractEmailTemplate<HardwareExpiredNotification> {
    public HardwareExpiredTemplate(TemplateEngine emailTemplateEngine, FrontendProperties frontendProperties) {
        super(emailTemplateEngine, frontendProperties);
    }

    @Override
    public Class<HardwareExpiredNotification> getNotificationClass() {
        return HardwareExpiredNotification.class;
    }

    @Override
    protected void enrichContext(Context context, HardwareExpiredNotification notification) {
        HardwareDto hardwareDto = notification.getPayload();
        context.setVariable("hardwareDto", hardwareDto);
    }

    @Override
    public String getBodyTemplateName() {
        return "body/hardware-expired-event";
    }

    @Override
    public String getSubjectTemplateName() {
        return "subject/hardware-expired-event";
    }
}
