package ru.prooftechit.smh.api.service;

import org.springframework.data.domain.Pageable;
import ru.prooftechit.smh.api.dto.PageWithUnread;
import ru.prooftechit.smh.api.dto.UserEventDto;
import ru.prooftechit.smh.api.enums.UserEventType;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.search.with_metadata.UserEventMetadataSpecification;

/**
 * Сервис по работе с важными событиями пользователей.
 */
public interface UserEventService {

    PageWithUnread<UserEventDto> getUserEvents(UserEventMetadataSpecification specification, Pageable pageable);
    void readUserEvent(Long userEventId, User user);
    void create(Object payloadDto, UserEventType userEventType, Long facilityId);
}
