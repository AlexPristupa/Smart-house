package ru.prooftechit.smh.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.api.dto.PageWithUnread;
import ru.prooftechit.smh.api.dto.UserEventDto;
import ru.prooftechit.smh.api.enums.UserEventType;
import ru.prooftechit.smh.api.service.UserEventMetadataService;
import ru.prooftechit.smh.api.service.UserEventService;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.UserEvent;
import ru.prooftechit.smh.domain.repository.UserEventRepository;
import ru.prooftechit.smh.domain.search.with_metadata.UserEventMetadataSpecification;
import ru.prooftechit.smh.exceptions.RecordNotFoundException;
import ru.prooftechit.smh.mapper.UserEventMapper;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventServiceImpl implements UserEventService {

    private final ObjectMapper objectMapper;
    private final UserEventMapper userEventMapper;
    private final UserEventRepository userEventRepository;
    private final UserEventMetadataService userEventMetadataService;

    @Override
    public void readUserEvent(Long userEventId, User user) {
        UserEvent userEvent = userEventRepository.findById(userEventId).orElseThrow(RecordNotFoundException::new);
        userEventMetadataService.markAsRead(userEvent.getId(), user.getId());
    }

    public PageWithUnread<UserEventDto> getUserEvents(UserEventMetadataSpecification specification, Pageable pageable) {
        return userEventRepository.findAllWithMetadata(specification, pageable).map(e -> userEventMapper.toDto(e.getEntity(), e.getMetadata()));
    }

    @Override
    public void create(Object payloadDto, UserEventType type, Long facilityId) {
        UserEvent userEvent = new UserEvent();
        try {
            userEvent.setPayload(objectMapper.writeValueAsString(payloadDto));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        userEvent.setType(type);
        userEvent.setFacilityId(facilityId);
        userEventRepository.save(userEvent);
    }

}
