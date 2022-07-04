package ru.prooftechit.smh.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.prooftechit.smh.api.dto.SessionDto;
import ru.prooftechit.smh.configuration.security.RefreshToken;
import ru.prooftechit.smh.domain.model.Session;
import ru.prooftechit.smh.domain.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Сервис по работе с сессиями пользователей.
 *
 * @author Andrey Kovalenko
 */
public interface SessionService {

    /**
     * Получить список сессий пользователя.
     *
     * @param userId идентификатор пользователя
     *
     * @return список сессий пользователя
     */
    Page<SessionDto> getSessions(Long userId, Pageable pageable);

    /**
     * Сохранить новую сессию
     *
     * @param request запрос
     * @param user пользователь
     * @param refreshToken токен
     */
    Session createSession(HttpServletRequest request, User user, RefreshToken refreshToken);

    /**
     * Получить сессию по uuid токена
     *
     * @param tokenUuid uuid токена
     *
     * @return сессия соответствующая токену
     */
    Optional<Session> getSessionByTokenUuid(String tokenUuid);

    /**
     * Сохранение информации о сессии веб сокет соединения
     *
     * @param sessionId id сессии
     * @param isOnline true - новое подключение веб сокета, false - отключение веб сокета
     */
    void updateWebSocketSessionInfo(Long sessionId, boolean isOnline);
}
