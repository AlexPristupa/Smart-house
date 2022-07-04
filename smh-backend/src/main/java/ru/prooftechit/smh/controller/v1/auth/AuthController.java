package ru.prooftechit.smh.controller.v1.auth;

import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftechit.smh.api.dto.auth.AuthRequestDto;
import ru.prooftechit.smh.api.dto.auth.AuthResponseDto;
import ru.prooftechit.smh.api.dto.auth.RefreshRequestDto;
import ru.prooftechit.smh.api.service.AuthService;
import ru.prooftechit.smh.api.v1.auth.AuthApi;
import ru.prooftechit.smh.controller.v1.AbstractController;

/**
 * @author Roman Zdoronok
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthController extends AbstractController implements AuthApi {

    private final AuthService authService;

    @Override
    public ResponseEntity<AuthResponseDto> login(AuthRequestDto requestDto, HttpServletRequest request) {
        return ResponseEntity.ok(authService.login(requestDto, request));
    }

    @Override
    @Transactional
    public ResponseEntity<AuthResponseDto> refresh(RefreshRequestDto requestDto) {
        return ResponseEntity.ok(authService.refresh(requestDto));
    }


}
