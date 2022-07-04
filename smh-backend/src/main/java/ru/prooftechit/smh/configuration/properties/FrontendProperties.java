package ru.prooftechit.smh.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author Roman Zdoronok
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "smh.frontend", ignoreUnknownFields = false)
public class FrontendProperties {

    @NestedConfigurationProperty
    private ServiceWorksPath serviceWorks = new ServiceWorksPath();

    private String host = "http://localhost:3000/";

    @Getter
    @Setter
    public static class ServiceWorksPath {
        private String viewPath = "Buildings/%d/Service/%d";
        private String resolvePath = "Buildings/%d/Service/%d";
        private String postponePath = "Buildings/%d/Service/%d";
    }

    public String getServiceWorkViewUrl(long facilityId, long serviceId) {
        return getHost() + getServiceWorks().getViewPath().formatted(facilityId, serviceId);
    }

    public String getServiceWorkResolveUrl(long facilityId, long serviceId) {
        return getHost() + getServiceWorks().getResolvePath().formatted(facilityId, serviceId);
    }
    public String getServiceWorkPostponeUrl(long facilityId, long serviceId) {
        return getHost() + getServiceWorks().getPostponePath().formatted(facilityId, serviceId);
    }
}
