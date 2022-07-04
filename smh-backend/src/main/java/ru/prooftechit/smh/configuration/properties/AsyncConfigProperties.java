package ru.prooftechit.smh.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Andrey Kovalenko
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "smh.async", ignoreUnknownFields = false)
public class AsyncConfigProperties {

    public static final int DEFAULT_CORE_POOL_SIZE = 2;
    public static final int DEFAULT_MAX_POOL_SIZE = 10;

    private int corePoolSize = DEFAULT_CORE_POOL_SIZE;
    private int maxPoolSize = DEFAULT_MAX_POOL_SIZE;
}
