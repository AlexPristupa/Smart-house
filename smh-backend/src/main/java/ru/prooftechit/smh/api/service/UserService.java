package ru.prooftechit.smh.api.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.api.dto.user.UserRequestDto;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.search.UserSpecification;

/**
 * Сервис по работе с профилями пользователей.
 *
 * @author Roman Zdoronok
 */
public interface UserService {

    UserDto getUser(Long userId, User user);
    UserDto updateUser(Long userId, UserRequestDto userRequestDto, User user);
    UserDto updateUserRole(Long userId, UserRole role, User user);
    Page<UserDto> getUsers(User user, UserSpecification specification, Pageable pageable);
    void deleteUser(Long userId, User user);
}
