package ru.prooftechit.smh.api.service;

import org.springframework.content.commons.repository.ContentStore;
import ru.prooftechit.smh.domain.model.File;

/**
 * Сервис на физическое сохранение файла.
 *
 * @author Yan Yukhnovets
 */
public interface FileContentStore extends ContentStore<File, String> {

}
