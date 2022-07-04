package ru.prooftechit.smh.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.api.dto.user.UserRequestDto;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.api.service.UserService;
import ru.prooftechit.smh.api.v1.UserApi;
import ru.prooftechit.smh.domain.search.UserSpecification;

import java.util.Set;

/**
 * @author Roman Zdoronok
 */
@RestController
@RequiredArgsConstructor
public class UserController extends AbstractController implements UserApi {

    private final UserService userService;

    @Override
    public ResponseEntity<UserDto> getUser(Long userId) {
        return ResponseEntity.ok(userService.getUser(userId, getCurrentUser()));
    }

    @Override
    public ResponseEntity<UserDto> updateUser(Long userId, UserRequestDto user) {
        return ResponseEntity.ok(userService.updateUser(userId, user, getCurrentUser()));
    }

    @Override
    public ResponseEntity<UserDto> updateUserRole(Long userId, UserRole role) {
        return ResponseEntity.ok(userService.updateUserRole(userId, role, getCurrentUser()));
    }

    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        userService.deleteUser(userId, getCurrentUser());
        return ResponseEntity.noContent().build();
    }

    @Override
    public Page<UserDto> getUsers(String search, Set<UserRole> roles, Pageable pageable) {
        UserSpecification userSpecification = new UserSpecification();
        userSpecification.setRoles(roles)
                .setSearch(search);
        return userService.getUsers(getCurrentUser(), userSpecification, pageable);
    }
}
