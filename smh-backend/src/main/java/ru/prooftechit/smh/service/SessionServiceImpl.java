package ru.prooftechit.smh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prooftechit.smh.api.dto.SessionDto;
import ru.prooftechit.smh.api.service.SessionService;
import ru.prooftechit.smh.configuration.security.RefreshToken;
import ru.prooftechit.smh.domain.model.Session;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.repository.SessionRepository;
import ru.prooftechit.smh.mapper.SessionMapper;
import ua_parser.Client;
import ua_parser.Parser;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Реализация сервиса по работе с сессиями пользователей.
 *
 * @author Andrey Kovalenko
 */
@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private static final String USER_AGENT_HEADER_NAME = "user-agent";
    private static final String X_FORWARDED_FOR_HEADER_NAME = "x-forwarded-for";
    private static final String X_FORWARDED_FOR_REGEX = " *, *";

    private final SessionRepository sessionRepository;
    private final SessionMapper sessionMapper;
    private final Parser parser = new Parser();

    @Override
    public Page<SessionDto> getSessions(Long userId, Pageable pageable) {
        return sessionRepository.findAllByUser_IdOrderByCreatedAtDesc(userId, pageable).map(sessionMapper::toDto);
    }

    @Override
    public Session createSession(HttpServletRequest request, User user, RefreshToken refreshToken) {
        Client client = parser.parse(request.getHeader(USER_AGENT_HEADER_NAME));
        return sessionRepository.save(Session.builder()
                .browser(Optional.ofNullable(client.userAgent).map(ua -> ua.family).orElse(null))
                .os(Optional.ofNullable(client.os).map(os -> os.family).orElse(null))
                .ip(extractIp(request))
                .user(user)
                .tokenUuid(refreshToken.getUuid())
                .expireAt(refreshToken.getExpireAt())
                .build());
    }

    @Override
    public Optional<Session> getSessionByTokenUuid(String tokenUuid) {
        return Optional.ofNullable(sessionRepository.findByTokenUuid(tokenUuid));
    }

    @Override
    public void updateWebSocketSessionInfo(Long sessionId, boolean isOnline) {
        Optional<Session> sessionOptional = sessionRepository.findById(sessionId);
        if(sessionOptional.isPresent()) {
            Session session = sessionOptional.get();
            session.setOnline(isOnline);
            sessionRepository.save(session);
        }
        else {
            log.warn("Не найдена указанная в токене сессия: {}", sessionId);
        }

    }

    private String extractIp(HttpServletRequest request) {
        String clientXForwardedForIp = request.getHeader(X_FORWARDED_FOR_HEADER_NAME);
        return Optional.ofNullable(clientXForwardedForIp).map(c -> c.split(X_FORWARDED_FOR_REGEX))
                .filter(arr -> arr.length > 0).map(arr -> arr[0]).orElse(request.getRemoteAddr());
    }
}
