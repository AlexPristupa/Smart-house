package ru.prooftechit.smh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prooftechit.smh.api.dto.error.ValidationErrorResponseDto;
import ru.prooftechit.smh.api.dto.registration.UserRegistrationRequest;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.api.enums.UserStatus;
import ru.prooftechit.smh.api.service.RegistrationService;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.User_;
import ru.prooftechit.smh.domain.repository.UserRepository;
import ru.prooftechit.smh.exceptions.CommonValidationException;
import ru.prooftechit.smh.mapper.RegistrationMapper;
import ru.prooftechit.smh.mapper.UserMapper;
import ru.prooftechit.smh.validation.ErrorMessages;

import java.util.*;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RegistrationServiceImpl implements RegistrationService {

    private final PasswordEncoder passwordEncoder;


    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final RegistrationMapper registrationMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public UserDto registerNewUser(UserRegistrationRequest request) {
        User user = registrationMapper.toUser(request);

        validateUsername(request, user);

        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(UserRole.READER);
        user.setStatus(UserStatus.ACTIVE);

        return userMapper.toUserDto(userRepository.save(user));
    }


    private void validateUsername(UserRegistrationRequest request, User user) {
        Long userId = user == null ? null : user.getId();
        List<ValidationErrorResponseDto> errors = new ArrayList<>();
        if (userRepository.existsByEmailExceptUser(request.getEmail(), userId)) {
            errors.add(ValidationErrorResponseDto.builder()
                                                 .formName(request.getClass().getSimpleName())
                                                 .fieldName(User_.EMAIL)
                                                 .rejectedValue(request.getEmail())
                                                 .message(ErrorMessages.EMAIL_NOT_UNIQUE)
                                                 .build());
        }

        if (!errors.isEmpty()) {
            throw new CommonValidationException(errors);
        }

    }
}
