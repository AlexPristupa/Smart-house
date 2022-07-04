package ru.prooftechit.smh.api.service;

import ru.prooftechit.smh.domain.model.metadata.UserEventMetadata;

/**
 * Сервис по работе с метаданными важных событиями пользователей
 */
public interface UserEventMetadataService {
    void markAsRead(long userEventId, long userId);
    UserEventMetadata findOrCreateMetadata(long userEventId, long userId);
}
