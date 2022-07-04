package ru.prooftechit.smh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.api.service.UserEventMetadataService;
import ru.prooftechit.smh.domain.model.metadata.UserEventMetadata;
import ru.prooftechit.smh.domain.model.metadata.EntityMetadataKey;
import ru.prooftechit.smh.domain.repository.UserEventMetadataRepository;

import java.util.function.UnaryOperator;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserEventMetadataServiceImpl implements UserEventMetadataService {

    private final UserEventMetadataRepository userEventMetadataRepository;

    @Override
    public void markAsRead(long userEventId, long userId) {
        applyMeta(userEventId, userId, userEventMetadata -> (UserEventMetadata) userEventMetadata.setRead(true));
    }

    public UserEventMetadata findOrCreateMetadata(long userEventId, long userId) {
        EntityMetadataKey id = new EntityMetadataKey(userEventId, userId);
        return userEventMetadataRepository.findById(id)
                                          .orElseGet(() -> {
                                              UserEventMetadata metadata = new UserEventMetadata();
                                              metadata.setId(id);
                                              return userEventMetadataRepository.save(metadata);
                                          });
    }

    private void applyMeta(long userEventId, long userId, UnaryOperator<UserEventMetadata> modifier) {
        modifier.andThen(userEventMetadataRepository::save)
                .apply(findOrCreateMetadata(userEventId, userId));
    }
}
