package ru.prooftechit.smh.configuration.websockets;

import java.security.Principal;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import ru.prooftechit.smh.configuration.security.JwtTokenProvider;
import ru.prooftechit.smh.configuration.security.SecurityContextHolderWrapper;
import ru.prooftechit.smh.configuration.security.claims.AccessTokenClaims;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class ClientInboundInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (accessor != null) {
            StompCommand command = accessor.getCommand();
            if (command == StompCommand.CONNECT) {
                String authorization = accessor.getFirstNativeHeader(HttpHeaders.AUTHORIZATION);
                Optional<AccessTokenClaims> claims = jwtTokenProvider.resolveTokenHeader(authorization);
                if (claims.isPresent()) {
                    UserDetails userDetails = userDetailsService.loadUserByUsername(claims.get().getUsername());
                    Authentication auth = SecurityContextHolderWrapper.setAuthentication(userDetails, claims.get());
                    accessor.setUser(auth);
                } else {
                    return null;
                }
            }
        }

        return message;
    }

    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel channel, boolean sent, Exception ex) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand stompCommand = accessor.getCommand();
        if (stompCommand != null) {
            Principal principal = accessor.getUser();
            if (stompCommand == StompCommand.SUBSCRIBE) {//TODO: implement important events logic
                String sessionId = accessor.getSessionId();
//                applicationContext.publishEvent(new CheckMessageEvent("", userId,sessionId));
            }
        }
    }
}
