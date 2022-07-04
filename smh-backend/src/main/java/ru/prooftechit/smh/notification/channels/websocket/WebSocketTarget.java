package ru.prooftechit.smh.notification.channels.websocket;

import java.util.Map;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.configuration.websockets.WebSocketConfiguration;

/**
 * @author Roman Zdoronok
 */
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class WebSocketTarget {

    private static final Map<UserRole, String> ROLE_TO_DESTINATION_MAP =
        Map.of(UserRole.READER, WebSocketConfiguration.EVENTS_QUEUE,
               UserRole.WRITER, WebSocketConfiguration.EVENTS_QUEUE,
               UserRole.ADMIN, WebSocketConfiguration.ADMIN_QUEUE,
               UserRole.ROOT, WebSocketConfiguration.ADMIN_QUEUE);

    private final String queue;
    private final String user;

    public static WebSocketTarget forRole(UserRole role) {
        return new WebSocketTarget(ROLE_TO_DESTINATION_MAP.get(role), null);
    }

    public static WebSocketTarget forUser(String username) {
        return new WebSocketTarget(WebSocketConfiguration.EVENTS_QUEUE, username);
    }

    public static WebSocketTarget forAll() {
        return new WebSocketTarget(WebSocketConfiguration.EVENTS_QUEUE, null);
    }
}
