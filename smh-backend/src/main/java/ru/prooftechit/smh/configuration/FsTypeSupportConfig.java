package ru.prooftechit.smh.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.content.fs.config.EnableFilesystemStores;
import org.springframework.content.fs.io.FileSystemResourceLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.prooftechit.smh.configuration.properties.FileSystemResourceProperties;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Конфигурации пути хранения файлов.
 *
 * @author Yukhnovets Yan
 */
@Configuration
@EnableFilesystemStores
@RequiredArgsConstructor
public class FsTypeSupportConfig {

  private final FileSystemResourceProperties fileSystemResourceProperties;

  @Bean
  File filesystemRoot() throws IOException {
    Path root = fileSystemResourceProperties.getRoot();
    if (!Files.exists(root)) {
      Files.createDirectories(root);
    }
    return root.toFile();
  }

  @Bean
  FileSystemResourceLoader fileSystemResourceLoader() throws IOException {
    return new FileSystemResourceLoader(filesystemRoot().getAbsolutePath());
  }
}
