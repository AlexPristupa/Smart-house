package ru.prooftechit.smh.api.service;

import ru.prooftechit.smh.api.dto.registration.UserRegistrationRequest;
import ru.prooftechit.smh.api.dto.user.UserDto;

/**
 * @author Roman Zdoronok
 */
public interface RegistrationService {
    UserDto registerNewUser(UserRegistrationRequest request);
}
