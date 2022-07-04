package ru.prooftechit.smh.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftechit.smh.api.dto.UserEventDto;
import ru.prooftechit.smh.api.service.UserEventService;
import ru.prooftechit.smh.api.v1.UserEventApi;
import ru.prooftechit.smh.domain.search.UserEventSpecification;
import ru.prooftechit.smh.domain.search.with_metadata.UserEventWithMetadataSpecification;

@RestController
@RequiredArgsConstructor
public class UserEventController extends AbstractController implements UserEventApi {

    private final UserEventService userEventService;

    @Override
    public Page<UserEventDto> getUserEvents(Boolean unread, Pageable pageable) {
        UserEventWithMetadataSpecification metadataSpecification =
            new UserEventWithMetadataSpecification(getCurrentUser(), new UserEventSpecification());
        metadataSpecification.setUnread(unread);
        return userEventService.getUserEvents(metadataSpecification, pageable);
    }

    @Override
    public ResponseEntity<?> readUserEvent(Long userEventId) {
        userEventService.readUserEvent(userEventId, getCurrentUser());
        return ResponseEntity.noContent().build();
    }

}
