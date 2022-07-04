package ru.prooftechit.smh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.api.dto.HardwareDto;
import ru.prooftechit.smh.api.dto.HardwareRequestDto;
import ru.prooftechit.smh.api.dto.error.ValidationErrorResponseDto;
import ru.prooftechit.smh.api.service.FileStoringService;
import ru.prooftechit.smh.api.service.HardwareService;
import ru.prooftechit.smh.domain.model.*;
import ru.prooftechit.smh.domain.repository.HardwareRepository;
import ru.prooftechit.smh.domain.search.HardwareSpecification;
import ru.prooftechit.smh.domain.util.PagedRepositoryReader;
import ru.prooftechit.smh.event.model.hardware.HardwareChangedEvent;
import ru.prooftechit.smh.event.model.hardware.HardwareCreatedEvent;
import ru.prooftechit.smh.event.model.hardware.HardwareDeletedEvent;
import ru.prooftechit.smh.exceptions.AmbiguousRequestException;
import ru.prooftechit.smh.exceptions.CommonValidationException;
import ru.prooftechit.smh.exceptions.RecordNotFoundException;
import ru.prooftechit.smh.mapper.HardwareMapper;
import ru.prooftechit.smh.service.internal.AccessRightsService;
import ru.prooftechit.smh.service.internal.HardwareServiceInternal;
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
public class HardwareServiceImpl implements HardwareService, HardwareServiceInternal {
    private final HardwareRepository hardwareRepository;
    private final HardwareMapper hardwareMapper;
    private final AccessRightsService accessRightsService;
    private final FileStoringService fileStoringService;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public HardwareDto saveHardware(long facilityId,
                                    HardwareRequestDto hardwareRequestDto,
                                    MultipartFile photo,
                                    List<MultipartFile> images,
                                    User user) {
        Facility facility = accessRightsService.getFacility(facilityId, user);
        validateModelWithSerialNumber(hardwareRequestDto.getModel(), hardwareRequestDto.getSerialNumber(), null);
        Hardware hardware = hardwareMapper.toEntity(hardwareRequestDto);

        UUID newPhotoUUID = hardwareRequestDto.getPhoto();
        UUID oldPhotoUUID = Optional.ofNullable(facility.getPhoto()).map(File::getContentId).orElse(null);

        boolean hasNewPhotoLink = newPhotoUUID != null && !newPhotoUUID.equals(oldPhotoUUID);
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
            hardware.setImages(imageFiles);
        }
        hardware.setFacility(facility);
        hardware.setPhoto(photoFile);
        applicationEventPublisher.publishEvent(new HardwareCreatedEvent(hardware));
        return hardwareMapper.toDto(hardwareRepository.save(hardware));
    }

    @Override
    public HardwareDto updateHardware(long hardwareId,
                                      HardwareRequestDto hardwareRequestDto,
                                      MultipartFile photo,
                                      List<MultipartFile> images,
                                      User user) {
        Hardware hardware = accessRightsService.getHardware(hardwareId, user);
        validateModelWithSerialNumber(hardwareRequestDto.getModel(), hardwareRequestDto.getSerialNumber(), hardwareId);
        hardware = hardwareMapper.toEntity(hardwareRequestDto, hardware);

        UUID newPhotoUUID = hardwareRequestDto.getPhoto();
        UUID oldPhotoUUID = Optional.ofNullable(hardware.getPhoto()).map(File::getContentId).orElse(null);

        boolean hasNewPhotoLink = newPhotoUUID != null && !newPhotoUUID.equals(oldPhotoUUID);
        boolean hasNewPhotoUpload = photo != null;

        if (hasNewPhotoLink && hasNewPhotoUpload) {
            throw new AmbiguousRequestException(Facility_.PHOTO);
        }

        if (hasNewPhotoUpload) {
            File photoFile = fileStoringService.saveFile(photo, user);
//            if (hardware.getPhoto() != null) {
            //TODO: вероятно нужно будет удалять старый файл?
//            }
            hardware.setPhoto(photoFile);
        } else if (hasNewPhotoLink) {
            // При редактировании указали другой идентификатор файла
            //TODO: вероятно нужно будет удалять старый файл?
            File photoFile = fileStoringService.getFile(newPhotoUUID, user).orElseThrow(RecordNotFoundException::new);
            hardware.setPhoto(photoFile);
        } else if (newPhotoUUID == null) {
            hardware.setPhoto(null);
        }

        Set<UUID> newImageUUIDs = Optional.ofNullable(hardwareRequestDto.getImages()).orElseGet(Collections::emptySet);
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
                                                                           .fieldName(Hardware_.IMAGES)
                                                                           .formName(hardwareRequestDto.getClass()
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

        hardware.getImages().clear();
        hardware.getImages().addAll(fetchedFiles);
        hardware.getImages().addAll(uploadedImageFiles);
        applicationEventPublisher.publishEvent(new HardwareChangedEvent(hardware));
        return hardwareMapper.toDto(hardwareRepository.save(hardware));
    }

    @Override
    public HardwareDto getHardware(long hardwareId, User user) {
        return hardwareMapper.toDto(accessRightsService.getHardware(hardwareId, user));
    }

    @Override
    public void deleteHardware(long hardwareId, User user) {
        deleteHardware(accessRightsService.getHardware(hardwareId, user));
    }

    @Override
    public void deleteHardware(Hardware hardware) {
        log.debug("Удаляется оборудование {}...", hardware.getId());
        if (hardware.getPhoto() != null) {
            fileStoringService.deleteFile(hardware.getPhoto());
        }
        if (hardware.getImages() != null) {
            hardware.getImages().forEach(fileStoringService::deleteFile);
        }
        hardwareRepository.delete(hardware);
        applicationEventPublisher.publishEvent(new HardwareDeletedEvent(hardware));
        log.debug("Оборудование {} удалено.", hardware.getId());
    }

    @Override
    public void deleteFacilityHardware(Facility facility) {
        PagedRepositoryReader<Hardware> reader = new PagedRepositoryReader<>(
                pageable -> hardwareRepository.findAllByFacility(facility, pageable), true);
        int deleted = reader.read(hardware -> {
            log.debug("Удаляется оборудование объекта {} ({} из {})...",
                      facility.getId(),
                      hardware.getNumberOfElements(),
                      hardware.getTotalElements());
            hardware.forEach(this::deleteHardware);
            return false;
        });
        log.debug("Оборудование объекта {} удалено ({}).", facility.getId(), deleted);
    }

    @Override
    public Page<HardwareDto> getHardware(User user, HardwareSpecification specification, Pageable pageable) {
        Page<Hardware> page = hardwareRepository.findAll(specification, pageable);
        return page.map(hardwareMapper::toDto);
    }

    @Override
    public Page<HardwareDto> getHardware(long facilityId, User user, HardwareSpecification specification, Pageable pageable) {
        Facility facility = accessRightsService.getFacility(facilityId, user);
        specification.setFacility(facility);
        Page<Hardware> page = hardwareRepository.findAll(specification, pageable);
        return page.map(hardwareMapper::toDto);
    }

    private void validateModelWithSerialNumber(String model, String serialNumber, Long id) {

        if (hardwareRepository.existsByModelAndSerialNumberExceptItself(model, serialNumber, id)) {
            List<ValidationErrorResponseDto> validationErrorResponseDtos = new ArrayList<>();
                validationErrorResponseDtos.add(ValidationErrorResponseDto.builder()
                    .formName(HardwareRequestDto.class.getSimpleName())
                    .fieldName(Hardware_.MODEL)
                    .rejectedValue(model)
                    .message(ErrorMessages.MODEL_WITH_SERIAL_NUMBER_NOT_UNIQUE)
                    .build());
            validationErrorResponseDtos.add(ValidationErrorResponseDto.builder()
                    .formName(HardwareRequestDto.class.getSimpleName())
                    .fieldName(Hardware_.SERIAL_NUMBER)
                    .rejectedValue(serialNumber)
                    .message(ErrorMessages.MODEL_WITH_SERIAL_NUMBER_NOT_UNIQUE)
                    .build());
            throw new CommonValidationException(validationErrorResponseDtos);
        }
    }
}
