package ru.prooftechit.smh.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

/**
 * @author Roman Zdoronok
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "smh.storage", ignoreUnknownFields = false)
public class FileSystemResourceProperties {
    public static final String STORAGE_DIR_NAME = "file-storage";
    public static final int DEFAULT_FILE_PREVIEW_TARGET_WIDTH = 296;
    public static final int DEFAULT_FILE_PREVIEW_TARGET_HEIGHT = 197;

    private Path root = Path.of(System.getProperty("user.dir"), STORAGE_DIR_NAME);
    private int filePreviewTargetWidth = DEFAULT_FILE_PREVIEW_TARGET_WIDTH;
    private int filePreviewTargetHeight = DEFAULT_FILE_PREVIEW_TARGET_HEIGHT;
}
