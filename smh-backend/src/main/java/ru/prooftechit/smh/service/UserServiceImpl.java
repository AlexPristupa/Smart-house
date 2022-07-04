package ru.prooftechit.smh.service;

import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.api.dto.user.UserRequestDto;
import ru.prooftechit.smh.api.enums.UserRole;
import ru.prooftechit.smh.api.enums.UserStatus;
import ru.prooftechit.smh.api.service.UserService;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.repository.UserRepository;
import ru.prooftechit.smh.domain.search.UserSpecification;
import ru.prooftechit.smh.exceptions.RestrictedAccessException;
import ru.prooftechit.smh.mapper.UserMapper;
import ru.prooftechit.smh.service.internal.AccessRightsService;
import ru.prooftechit.smh.validation.ErrorMessages;

import java.util.*;

/**
 * @author Roman Zdoronok
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private static final Map<UserRole, Set<UserRole>> USER_ROLE_SUBS_MAP = Map.of(
        UserRole.ROOT, Collections.unmodifiableSet(EnumSet.allOf(UserRole.class)),
        UserRole.ADMIN, Collections.unmodifiableSet(EnumSet.of(UserRole.ADMIN, UserRole.WRITER, UserRole.READER))
        );

    public static final String SEPARATOR = " ";

    private final AccessRightsService accessRightsService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getUser(Long userId, User user) {
        return userMapper.toUserDto(accessRightsService.getUser(userId, user));
    }

    @Override
    public UserDto updateUser(Long userId, UserRequestDto userRequestDto, User user) {
        User updatableUser = accessRightsService.getUser(userId, user);
        UserRole oldRole = updatableUser.getRole();
        UserRole newRole = Optional.ofNullable(userRequestDto.getRole())
                                   .orElse(oldRole);
        checkRoleAccessRights(user, oldRole, newRole);
        updatableUser = userMapper.toEntity(userRequestDto, updatableUser);
        return userMapper.toUserDto(userRepository.save(updatableUser));
    }

    @Override
    public UserDto updateUserRole(Long userId, UserRole role, User user) {
        User updatableUser = accessRightsService.getUser(userId, user);
        UserRole oldRole = updatableUser.getRole();
        checkSelfEditingAccessRights(user, updatableUser);
        checkRoleAccessRights(user, oldRole, role);
        updatableUser.setRole(role);
        return userMapper.toUserDto(userRepository.save(updatableUser));
    }

    @Override
    public Page<UserDto> getUsers(User user, UserSpecification specification, Pageable pageable) {
        return userRepository.findAll(specification, pageable).map(userMapper::toUserDto);
    }

    @Override
    public void deleteUser(Long userId, User user) {
        User updatableUser = accessRightsService.getUser(userId, user);
        checkSelfEditingAccessRights(user, updatableUser);
        checkRoleAccessRights(user, updatableUser.getRole());
        updatableUser.setStatus(UserStatus.DELETED);
        updatableUser.setEmail(updatableUser.getEmail() + SEPARATOR + UUID.randomUUID());
        userRepository.save(updatableUser);
    }

    protected void checkSelfEditingAccessRights(User editor, User updatableUser) {
        if (editor.getId().equals(updatableUser.getId())) {
            throw new RestrictedAccessException(ErrorMessages.INSUFFICIENT_RIGHTS);
        }
    }

    protected void checkRoleAccessRights(User editor, UserRole... roles) {
        Map<UserRole, Set<UserRole>> userRoleSubsMap = getUserRoleSubsMap();
        Set<UserRole> currentUserRoleSubs = userRoleSubsMap.getOrDefault(editor.getRole(), Collections.emptySet());
        if(Arrays.stream(roles).anyMatch(Predicate.not(currentUserRoleSubs::contains))) {
            throw new RestrictedAccessException(ErrorMessages.INSUFFICIENT_RIGHTS);
        }

    }

    protected Map<UserRole, Set<UserRole>> getUserRoleSubsMap() {
        return USER_ROLE_SUBS_MAP;
    }
}
