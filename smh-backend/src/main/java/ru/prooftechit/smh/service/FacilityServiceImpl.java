package ru.prooftechit.smh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.api.dto.FacilityDto;
import ru.prooftechit.smh.api.dto.FacilityRequestDto;
import ru.prooftechit.smh.api.dto.error.ValidationErrorResponseDto;
import ru.prooftechit.smh.api.service.FacilityService;
import ru.prooftechit.smh.api.service.FileStoringService;
import ru.prooftechit.smh.domain.model.Facility;
import ru.prooftechit.smh.domain.model.Facility_;
import ru.prooftechit.smh.domain.model.File;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.repository.FacilityRepository;
import ru.prooftechit.smh.domain.search.FacilitySpecification;
import ru.prooftechit.smh.exceptions.AmbiguousRequestException;
import ru.prooftechit.smh.exceptions.CommonValidationException;
import ru.prooftechit.smh.exceptions.RecordNotFoundException;
import ru.prooftechit.smh.mapper.FacilityMapper;
import ru.prooftechit.smh.service.internal.*;
import ru.prooftechit.smh.validation.ErrorMessages;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Roman Zdoronok
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService, FacilityServiceInternal {

    private final FacilityRepository facilityRepository;
    private final FacilityMapper facilityMapper;
    private final FileStoringService fileStoringService;
    private final AccessRightsService accessRightsService;

    private final HardwareServiceInternal hardwareService;
    private final ServiceWorkServiceInternal serviceWorkService;
    private final DocumentTreeServiceInternal documentTreeService;

    @Override
    public FacilityDto saveFacility(FacilityRequestDto facilityRequestDto,
                                    MultipartFile photo,
                                    List<MultipartFile> images,
                                    User user) {
        validateNameAndAddressUniqueness(facilityRequestDto.getName(), facilityRequestDto.getAddress(), null);
        Facility entity = facilityMapper.toEntity(facilityRequestDto);
        UUID newPhotoUUID = facilityRequestDto.getPhoto();

        boolean hasNewPhotoLink = newPhotoUUID != null;
        boolean hasNewPhotoUpload = photo != null;

        if (hasNewPhotoLink && hasNewPhotoUpload) {
            throw new AmbiguousRequestException(Facility_.PHOTO);
        }
        
        File photoFile = photo == null ? null : fileStoringService.saveFile(photo, user);
        if (images != null) {
            Set<File> imageFiles = images.stream()
                                         .filter(Objects::nonNull)
                                         .map(multipart -> fileStoringService.saveFile(multipart, user))
                                         .collect(Collectors.toSet());
            entity.setImages(imageFiles);
        }
        entity.setPhoto(photoFile);
        return facilityMapper.toDto(facilityRepository.save(entity));
    }

    @Override
    public FacilityDto updateFacility(long facilityId,
                                      FacilityRequestDto facilityRequestDto,
                                      MultipartFile photo,
                                      List<MultipartFile> images,
                                      User user) {
        Facility facility = accessRightsService.getFacility(facilityId, user);
        validateNameAndAddressUniqueness(facilityRequestDto.getName(), facilityRequestDto.getAddress(), facilityId);
        facility = facilityMapper.toEntity(facilityRequestDto, facility);

        UUID newPhotoUUID = facilityRequestDto.getPhoto();
        UUID oldPhotoUUID = Optional.ofNullable(facility.getPhoto()).map(File::getContentId).orElse(null);

        boolean hasNewPhotoLink = newPhotoUUID != null && !newPhotoUUID.equals(oldPhotoUUID);
        boolean hasNewPhotoUpload = photo != null;

        if (hasNewPhotoLink && hasNewPhotoUpload) {
            throw new AmbiguousRequestException(Facility_.PHOTO);
        }

        if (hasNewPhotoUpload) {
            File photoFile = fileStoringService.saveFile(photo, user);
//            if (facility.getAvatar() != null) {
            //TODO: вероятно нужно будет удалять старый файл?
//            }
            facility.setPhoto(photoFile);
        } else if(hasNewPhotoLink) {
            // При редактировании указали другой идентификатор файла
            //TODO: вероятно нужно будет удалять старый файл?
            File photoFile = fileStoringService.getFile(newPhotoUUID, user).orElseThrow(RecordNotFoundException::new);
            facility.setPhoto(photoFile);
        } else if(newPhotoUUID == null) {
            facility.setPhoto(null);
        }


        //Возможно, старые, неиспользуемые файлы нужно будет удалять. Пока их не ищем
//        Set<UUID> oldImageUUIDs = facility.getImages()
//                                          .stream()
//                                          .map(File::getContentId)
//                                          .collect(Collectors.toSet());

        Set<UUID> newImageUUIDs = Optional.ofNullable(facilityRequestDto.getImages()).orElseGet(Collections::emptySet);
        List<File> fetchedFiles = newImageUUIDs.isEmpty()
                                  ? Collections.emptyList()
                                  : fileStoringService.getFiles(newImageUUIDs, user);
        Set<UUID> fetchedFileUUIDs = fetchedFiles.stream()
                                                 .map(File::getContentId)
                                                 .collect(Collectors.toSet());
        if (fetchedFileUUIDs.size() != newImageUUIDs.size()) {
            List<ValidationErrorResponseDto> errors = new ArrayList<>();
            newImageUUIDs.stream()
                         .filter(Predicate.not(fetchedFileUUIDs::contains))
                         .forEach(uuid ->
                                      errors.add(ValidationErrorResponseDto.builder()
                                                                           .message(ErrorMessages.FILE_NOT_EXIST)
                                                                           .fieldName(Facility_.IMAGES)
                                                                           .formName(facilityRequestDto.getClass()
                                                                                                       .getSimpleName())
                                                                           .rejectedValue(uuid.toString())
                                                                           .build())
                         );
            throw new CommonValidationException(errors);
        }

        Set<File> uploadedImageFiles = Optional.ofNullable(images)
                                               .orElseGet(Collections::emptyList)
                                               .stream()
                                               .filter(Objects::nonNull)
                                               .map(multipart -> fileStoringService.saveFile(multipart, user))
                                               .collect(Collectors.toSet());

        facility.getImages().clear();
        facility.getImages().addAll(fetchedFiles);
        facility.getImages().addAll(uploadedImageFiles);

        return facilityMapper.toDto(facilityRepository.save(facility));
    }

    @Override
    public FacilityDto getFacility(long facilityId, User user) {
        return facilityMapper.toDto(accessRightsService.getFacility(facilityId, user));
    }

    @Override
    public void deleteFacility(long facilityId, User user) {
        deleteFacility(accessRightsService.getFacility(facilityId, user));
    }

    @Override
    public void deleteFacility(Facility facility) {
        log.debug("Удаляется объект {}...", facility.getId());
        documentTreeService.deleteFacilityNodes(facility);
        serviceWorkService.deleteFacilityServiceWorks(facility);
        hardwareService.deleteFacilityHardware(facility);

        if(facility.getPhoto() != null) {
            fileStoringService.deleteFile(facility.getPhoto());
        }
        if(facility.getImages() != null) {
            facility.getImages().forEach(fileStoringService::deleteFile);
        }

        facilityRepository.delete(facility);
        log.debug("Объект {} удален.", facility.getId());
    }

    @Override
    public Page<FacilityDto> getFacilities(User user, FacilitySpecification specification, Pageable pageable) {
        Page<Facility> page = facilityRepository.findAll(specification, pageable);
        return page.map(facilityMapper::toDto);
    }

    private void validateNameAndAddressUniqueness(String name, String address, Long id) {
        if (facilityRepository.existsByNameAndAddressExceptItself(name, address, id)) {
            List<ValidationErrorResponseDto> validationErrorResponseDtos = new ArrayList<>();
            validationErrorResponseDtos.add(ValidationErrorResponseDto.builder()
                .formName(FacilityRequestDto.class.getSimpleName())
                .fieldName(Facility_.NAME)
                .rejectedValue(name)
                .message(ErrorMessages.NAME_WITH_ADDRESS_NOT_UNIQUE)
                .build());
            validationErrorResponseDtos.add(ValidationErrorResponseDto.builder()
                .formName(FacilityRequestDto.class.getSimpleName())
                .fieldName(Facility_.ADDRESS)
                .rejectedValue(address)
                .message(ErrorMessages.NAME_WITH_ADDRESS_NOT_UNIQUE)
                .build());
            throw new CommonValidationException(validationErrorResponseDtos);
        }
    }
}
