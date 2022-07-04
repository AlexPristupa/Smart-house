package ru.prooftechit.smh.configuration.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.repository.UserRepository;
import ru.prooftechit.smh.exceptions.user.UserNotFoundException;

/**
 * @author Roman Zdoronok
 */
@Component
@RequiredArgsConstructor
public class UserConverter implements Converter<String, User> {
    private final UserRepository userRepository;

    @Override
    public User convert(String s) {
        return userRepository.findById(Long.valueOf(s)).orElseThrow(UserNotFoundException::new);
    }
}
