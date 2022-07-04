package ru.prooftechit.smh.api.service;

import javax.servlet.http.HttpServletRequest;
import ru.prooftechit.smh.api.dto.auth.AuthRequestDto;
import ru.prooftechit.smh.api.dto.auth.AuthResponseDto;
import ru.prooftechit.smh.api.dto.auth.RefreshRequestDto;
import ru.prooftechit.smh.api.dto.auth.payload.AuthorizedUserInfo;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
public interface AuthService {
    AuthResponseDto login(AuthRequestDto requestDto, HttpServletRequest request);
    AuthResponseDto refresh(RefreshRequestDto requestDto);
    AuthorizedUserInfo getAuthorizedUserInfo(User user);
}
