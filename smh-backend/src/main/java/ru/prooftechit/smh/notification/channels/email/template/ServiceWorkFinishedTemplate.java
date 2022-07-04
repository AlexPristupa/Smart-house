package ru.prooftechit.smh.notification.channels.email.template;

import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import ru.prooftechit.smh.api.dto.FacilityDto;
import ru.prooftechit.smh.api.dto.ServiceWorkDto;
import ru.prooftechit.smh.configuration.properties.FrontendProperties;
import ru.prooftechit.smh.notification.model.ServiceWorkFinishedNotification;

@Component
public class ServiceWorkFinishedTemplate extends AbstractEmailTemplate<ServiceWorkFinishedNotification> {
    public ServiceWorkFinishedTemplate(TemplateEngine emailTemplateEngine, FrontendProperties frontendProperties) {
        super(emailTemplateEngine, frontendProperties);
    }

    @Override
    public Class<ServiceWorkFinishedNotification> getNotificationClass() {
        return ServiceWorkFinishedNotification.class;
    }

    @Override
    protected void enrichContext(Context context, ServiceWorkFinishedNotification notification) {
        ServiceWorkDto serviceWorkDto = notification.getPayload().getServiceWorkDto();
        FacilityDto facilityDto = notification.getPayload().getFacilityDto();
        context.setVariable("serviceWorkDto", serviceWorkDto);
        context.setVariable("resolveUrl",
            frontendProperties.getServiceWorkResolveUrl(facilityDto.getId(), serviceWorkDto.getId())
        );
        context.setVariable("postponeUrl",
            frontendProperties.getServiceWorkPostponeUrl(facilityDto.getId(), serviceWorkDto.getId())
        );
    }

    @Override
    public String getBodyTemplateName() {
        return "body/service-work-finished-event";
    }

    @Override
    public String getSubjectTemplateName() {
        return "subject/service-work-finished-event";
    }
}
