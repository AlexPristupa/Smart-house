package ru.prooftechit.smh.controller.v1.registration;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftechit.smh.api.dto.registration.UserRegistrationRequest;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.api.service.RegistrationService;
import ru.prooftechit.smh.api.v1.registration.RegistrationApi;
import ru.prooftechit.smh.controller.v1.AbstractController;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@Transactional
public class RegistrationController extends AbstractController implements RegistrationApi {

    private final RegistrationService registrationService;

    @Override
    public ResponseEntity<UserDto> newUserRegistration(UserRegistrationRequest request) {
        return ResponseEntity.ok(registrationService.registerNewUser(request));
    }

}
