package ru.prooftechit.smh.configuration.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.repository.UserRepository;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findOneByUsername(username)
                                  .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found"));

        log.debug("user with username: {} successfully loaded", username);
        return new UserDetailsImpl(username, user);
    }

}
