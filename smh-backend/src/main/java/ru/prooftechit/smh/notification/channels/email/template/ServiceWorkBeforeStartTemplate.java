package ru.prooftechit.smh.notification.channels.email.template;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.prooftechit.smh.api.dto.FacilityDto;
import ru.prooftechit.smh.api.dto.ServiceWorkDto;
import ru.prooftechit.smh.configuration.properties.FrontendProperties;
import ru.prooftechit.smh.notification.model.ServiceWorkBeforeStartNotification;

@Component
public class ServiceWorkBeforeStartTemplate extends AbstractEmailTemplate<ServiceWorkBeforeStartNotification> {

    public ServiceWorkBeforeStartTemplate(TemplateEngine emailTemplateEngine, FrontendProperties frontendProperties) {
        super(emailTemplateEngine, frontendProperties);
    }

    @Override
    public Class<ServiceWorkBeforeStartNotification> getNotificationClass() {
        return ServiceWorkBeforeStartNotification.class;
    }

    @Override
    protected void enrichContext(Context context, ServiceWorkBeforeStartNotification notification) {
        ServiceWorkDto serviceWorkDto = notification.getPayload().getServiceWorkDto();
        FacilityDto facilityDto = notification.getPayload().getFacilityDto();
        context.setVariable("serviceWorkDto", serviceWorkDto);
        context.setVariable("viewUrl",
            frontendProperties.getServiceWorkViewUrl(facilityDto.getId(), serviceWorkDto.getId())
        );
    }

    @Override
    public String getBodyTemplateName() {
        return "body/service-work-before-start-event";
    }

    @Override
    public String getSubjectTemplateName() {
        return "subject/service-work-before-start-event";
    }
}
