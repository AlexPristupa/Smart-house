package ru.prooftechit.smh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.api.dto.auth.AuthRequestDto;
import ru.prooftechit.smh.api.dto.auth.AuthResponseDto;
import ru.prooftechit.smh.api.dto.auth.RefreshRequestDto;
import ru.prooftechit.smh.api.dto.auth.payload.AuthorizedUserInfo;
import ru.prooftechit.smh.api.dto.auth.payload.UserRoleFeatures;
import ru.prooftechit.smh.api.enums.UserStatus;
import ru.prooftechit.smh.api.service.AuthService;
import ru.prooftechit.smh.api.service.SessionService;
import ru.prooftechit.smh.configuration.security.JwtAuthenticationException;
import ru.prooftechit.smh.configuration.security.JwtTokenProvider;
import ru.prooftechit.smh.configuration.security.RefreshToken;
import ru.prooftechit.smh.configuration.security.UserDetailsImpl;
import ru.prooftechit.smh.configuration.security.claims.RefreshTokenClaims;
import ru.prooftechit.smh.domain.model.Session;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.exceptions.user.UserNotFoundException;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final SessionService sessionService;

    @Override
    public AuthResponseDto login(AuthRequestDto requestDto, HttpServletRequest request) {
        String username = requestDto.getUsername();

        Object principal;
        try {
            Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            principal = authentication.getPrincipal();
        } catch (UsernameNotFoundException e) {
            throw new UserNotFoundException();
        }

        if (!(principal instanceof UserDetailsImpl)) {
            throw new UserNotFoundException();
        }

        User user = ((UserDetailsImpl) principal).getUser();
        //Проверяем, есть ли у пользователя право логиниться...
        if (UserStatus.NEW.equals(user.getStatus())) {
            // Пользователь зарегистрировался, но не подтвердил свою почту
            // Тут должна быть логика по получению юида регистрации и соответствующее исключение.
            throw new UserNotFoundException();
        }

        RefreshToken refreshToken = jwtTokenProvider.createRefreshToken(username, requestDto.isInsecurePc());
        Long sessionId = sessionService.createSession(request, user, refreshToken).getId();
        String accessToken = jwtTokenProvider.createAccessToken(username, user, requestDto.isInsecurePc(), sessionId);
        AuthorizedUserInfo authorizedUserInfo = getAuthorizedUserInfo(user);
        return new AuthResponseDto(accessToken, refreshToken.getToken(), authorizedUserInfo);
    }

    @Override
    public AuthResponseDto refresh(RefreshRequestDto requestDto) {
        String refreshToken = requestDto.getRefreshToken();
        RefreshTokenClaims claims = jwtTokenProvider.resolveRefreshToken(refreshToken)
                                                    .orElseThrow(
                                                        () -> new JwtAuthenticationException("Invalid jwt token"));

        Session session = sessionService.getSessionByTokenUuid(claims.getToken())
                                        .orElseThrow(
                                            () -> new JwtAuthenticationException("Active session was not found"));

        String username = claims.getUsername();
        Boolean insecure = claims.isInsecure();

        RefreshToken newRefreshTokenInfo = jwtTokenProvider.createRefreshToken(username, insecure);
        String accessToken = jwtTokenProvider.createAccessToken(username, session.getUser(), insecure, session.getId());
        session.setExpireAt(newRefreshTokenInfo.getExpireAt());
        session.setTokenUuid(newRefreshTokenInfo.getUuid());

        AuthorizedUserInfo authorizedUserInfo = getAuthorizedUserInfo(session.getUser());
        return new AuthResponseDto(accessToken, newRefreshTokenInfo.getToken(), authorizedUserInfo);
    }

    @Override
    public AuthorizedUserInfo getAuthorizedUserInfo(User user) {
        AuthorizedUserInfo info = new AuthorizedUserInfo()
            .setEmail(user.getEmail())
            .setStatus(user.getStatus());

        info.setRoleFeatures(new UserRoleFeatures()
                                 .setRole(user.getRole())
                                 .setFirstName(user.getFirstName())
                                 .setLastName(user.getLastName()));

        return info;
    }
}
