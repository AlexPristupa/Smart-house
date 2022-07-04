package ru.prooftechit.smh.configuration.properties;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.boot.convert.DurationUnit;

/**
 * @author Roman Zdoronok
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "smh.security", ignoreUnknownFields = false)
public class SecurityProperties {
    private static final int DEFAULT_PWD_STRENGTH = 12;
    private static final String DEFAULT_SECRET = "youwillneverguess";

    private int passwordEncodingStrength = DEFAULT_PWD_STRENGTH;

    @DurationUnit(ChronoUnit.SECONDS)
    private Duration passwordResetTimeout = Duration.ofMinutes(1);

    @NestedConfigurationProperty
    private Jwt jwt = new Jwt();

    @Getter
    @Setter
    public static class Jwt {
        private String secret = DEFAULT_SECRET;

        @NestedConfigurationProperty
        private TokenValidity regular = new TokenValidity(Duration.ofMinutes(10), Duration.ofDays(14));

        @NestedConfigurationProperty
        private TokenValidity insecure = new TokenValidity(Duration.ofMinutes(3), Duration.ofMinutes(10));
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TokenValidity {
        @DurationUnit(ChronoUnit.SECONDS)
        private Duration accessTokenValiditySeconds;
        @DurationUnit(ChronoUnit.SECONDS)
        private Duration refreshTokenValiditySeconds;
    }
}
