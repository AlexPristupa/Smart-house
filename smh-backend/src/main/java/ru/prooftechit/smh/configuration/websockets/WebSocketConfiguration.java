package ru.prooftechit.smh.configuration.websockets;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    public static final String EVENTS_QUEUE = "/events";
    public static final String ADMIN_QUEUE = "/admin";

    public static final String USER_DESINATION_PREFIX = "/user";

    private final ClientInboundInterceptor clientInboundInterceptor;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker(EVENTS_QUEUE,
                                  ADMIN_QUEUE);
        config.setUserDestinationPrefix(USER_DESINATION_PREFIX);
        config.setApplicationDestinationPrefixes("/api");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*")
                .withSockJS();
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(clientInboundInterceptor);
    }

}
