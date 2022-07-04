package ru.prooftechit.smh.service;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.api.dto.RequestFiles;
import ru.prooftechit.smh.api.dto.error.ValidationErrorResponseDto;
import ru.prooftechit.smh.api.dto.profile.ChangePasswordRequestDto;
import ru.prooftechit.smh.api.dto.profile.ProfileRequestDto;
import ru.prooftechit.smh.api.dto.user.UserDto;
import ru.prooftechit.smh.api.service.FileStoringService;
import ru.prooftechit.smh.api.service.ProfileService;
import ru.prooftechit.smh.domain.model.File;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.model.User_;
import ru.prooftechit.smh.domain.repository.UserRepository;
import ru.prooftechit.smh.exceptions.AmbiguousRequestException;
import ru.prooftechit.smh.exceptions.CommonValidationException;
import ru.prooftechit.smh.exceptions.RecordNotFoundException;
import ru.prooftechit.smh.mapper.ProfileMapper;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Roman Zdoronok
 */

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ProfileMapper profileMapper;
    private final FileStoringService fileStoringService;


    @Override
    public UserDto getProfile(User user) {
        return profileMapper.toDto(user);
    }

    @Override
    public UserDto updateProfile(ProfileRequestDto profile, RequestFiles requestFiles, User user) {
        User entity = profileMapper.toUser(profile, user);

        MultipartFile photo = requestFiles.getPhoto();

        UUID newPhotoUUID = profile.getPhoto();
        UUID oldPhotoUUID = Optional.ofNullable(user.getPhoto()).map(File::getContentId).orElse(null);

        boolean hasNewPhotoLink = newPhotoUUID != null && !newPhotoUUID.equals(oldPhotoUUID);
        boolean hasNewPhotoUpload = photo != null;

        if (hasNewPhotoLink && hasNewPhotoUpload) {
            throw new AmbiguousRequestException(User_.PHOTO);
        }

        if (hasNewPhotoUpload) {
            File photoFile = fileStoringService.saveFile(photo, user);
//            if (user.getPhoto() != null) {
            //TODO: вероятно нужно будет удалять старый файл?
//            }
            user.setPhoto(photoFile);
        } else if(hasNewPhotoLink) {
            // При редактировании указали другой идентификатор файла
            //TODO: вероятно нужно будет удалять старый файл?
            File photoFile = fileStoringService.getFile(newPhotoUUID, user).orElseThrow(RecordNotFoundException::new);
            user.setPhoto(photoFile);
        } else if(newPhotoUUID == null) {
            user.setPhoto(null);
        }
        return profileMapper.toDto(userRepository.save(entity));
    }

    @Override
    public void updatePassword(ChangePasswordRequestDto request, User user) {
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new CommonValidationException(
                ValidationErrorResponseDto.builder()
                                          .formName(ChangePasswordRequestDto.class.getSimpleName())
                                          .fieldName(ChangePasswordRequestDto.OLD_PASSWORD_FIELD)
                                          .message(ErrorMessages.INVALID_PASSWORD)
                                          .build());
        }
        userRepository.save(user.setPassword(passwordEncoder.encode(request.getPassword())));
    }
}
