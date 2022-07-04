package ru.prooftechit.smh.configuration.websockets;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;
import ru.prooftechit.smh.api.service.SessionService;
import ru.prooftechit.smh.configuration.security.JwtTokenProvider;
import ru.prooftechit.smh.configuration.security.SecurityContextHolderWrapper;
import ru.prooftechit.smh.configuration.security.claims.AccessTokenClaims;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class SocketEventsListener {

    private final JwtTokenProvider jwtTokenProvider;
    private final SessionService sessionService;


    @EventListener(SessionConnectEvent.class)
    public void handleWebsocketConnectListener(SessionConnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        updateSession(headers, true);
    }

    @EventListener(SessionDisconnectEvent.class)
    public void handleWebsocketDisconnectListner(SessionDisconnectEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        //TODO: не переводить сессию в офлайн, если есть другие подключения по вебсокету
        updateSession(headers, false);

    }

    private void updateSession(SimpMessageHeaderAccessor headers, boolean isOnline) {
        String authorization = headers.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
        Optional<AccessTokenClaims> claims = jwtTokenProvider.resolveTokenHeader(authorization);
        if (claims.isPresent()) {
            Long sessionId = claims.get().getSessionId();
            sessionService.updateWebSocketSessionInfo(sessionId, isOnline);
        }
    }
}
