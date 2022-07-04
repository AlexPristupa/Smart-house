package ru.prooftechit.smh.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Roman Zdoronok
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "smh.mail", ignoreUnknownFields = false)
public class MailProperties {

    private String from;
    private String fromAlias;
    private String supportEmail;
}
