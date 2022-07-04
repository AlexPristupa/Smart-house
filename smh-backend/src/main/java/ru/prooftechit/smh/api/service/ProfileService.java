package ru.prooftechit.smh.api.service;

import ru.prooftechit.smh.api.dto.RequestFiles;
import ru.prooftechit.smh.api.dto.profile.ChangePasswordRequestDto;
import ru.prooftechit.smh.api.dto.profile.ProfileRequestDto;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.domain.model.User;

/**
 * Сервис по работе с профилями пользователей.
 *
 * @author Roman Zdoronok
 */
public interface ProfileService {

    UserDto getProfile(User user);
    UserDto updateProfile(ProfileRequestDto profile, RequestFiles requestFiles, User user);
    void updatePassword(ChangePasswordRequestDto request, User user);

}
