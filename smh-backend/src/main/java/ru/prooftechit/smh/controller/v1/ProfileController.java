package ru.prooftechit.smh.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftechit.smh.api.dto.RequestFiles;
import ru.prooftechit.smh.api.dto.profile.ChangePasswordRequestDto;
import ru.prooftechit.smh.api.dto.profile.ProfileRequestDto;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.api.service.ProfileService;
import ru.prooftechit.smh.api.v1.ProfileApi;

/**
 * @author Roman Zdoronok
 */
@RestController
@RequiredArgsConstructor
public class ProfileController extends AbstractController implements ProfileApi {

    private final ProfileService profileService;

    @Override
    public ResponseEntity<UserDto> getProfile() {
        return ResponseEntity.ok(profileService.getProfile(getCurrentUser()));
    }

    @Override
    public ResponseEntity<UserDto> updateProfile(ProfileRequestDto profile, RequestFiles requestFiles) {
        return ResponseEntity.ok(profileService.updateProfile(profile, requestFiles, getCurrentUser()));
    }

    @Override
    public ResponseEntity<?> changePassword(ChangePasswordRequestDto password) {
        profileService.updatePassword(password, getCurrentUser());
        return  ResponseEntity.noContent().build();
    }
}
