package ru.prooftechit.smh.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.prooftechit.smh.api.dto.ServiceWorkDto;
import ru.prooftechit.smh.api.dto.ServiceWorkRequestDto;
import ru.prooftechit.smh.api.dto.ServiceWorkTypeDto;
import ru.prooftechit.smh.api.dto.ServiceWorkTypeRequestDto;
import ru.prooftechit.smh.api.dto.error.ValidationErrorResponseDto;
import ru.prooftechit.smh.api.enums.ServiceWorkResolution;
import ru.prooftechit.smh.api.enums.ServiceWorkStatus;
import ru.prooftechit.smh.api.service.ServiceWorkService;
import ru.prooftechit.smh.domain.model.*;
import ru.prooftechit.smh.domain.repository.ServiceWorkRepository;
import ru.prooftechit.smh.domain.repository.ServiceWorkTypeRepository;
import ru.prooftechit.smh.domain.search.ServiceWorkSpecification;
import ru.prooftechit.smh.domain.search.ServiceWorkTypeSpecification;
import ru.prooftechit.smh.domain.search.ServiceWorkTypeSpecification;
import ru.prooftechit.smh.domain.util.PagedRepositoryReader;
import ru.prooftechit.smh.event.model.service_work.*;
import ru.prooftechit.smh.exceptions.CommonValidationException;
import ru.prooftechit.smh.exceptions.RecordNotFoundException;
import ru.prooftechit.smh.mapper.ServiceWorkMapper;
import ru.prooftechit.smh.mapper.ServiceWorkTypeMapper;
import ru.prooftechit.smh.service.internal.AccessRightsService;
import ru.prooftechit.smh.service.internal.ServiceWorkServiceInternal;
import ru.prooftechit.smh.validation.ErrorMessages;

/**
 * @author Roman Zdoronok
 */
@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ServiceWorkServiceImpl implements ServiceWorkService, ServiceWorkServiceInternal {

    private final ServiceWorkRepository serviceWorkRepository;
    private final ServiceWorkTypeRepository serviceWorkTypeRepository;
    private final ServiceWorkMapper serviceWorkMapper;
    private final ServiceWorkTypeMapper serviceWorkTypeMapper;
    private final AccessRightsService accessRightsService;
    private final ApplicationEventPublisher applicationEventPublisher;

    private void validateServiceTypeUniqueness(Long serviceTypeId, String serviceTypeName) {
        if (serviceWorkTypeRepository.existsByNameExceptItself(serviceTypeId, serviceTypeName)) {
            throw new CommonValidationException(ValidationErrorResponseDto.builder()
                                                                          .formName(ServiceWorkTypeRequestDto.class.getSimpleName())
                                                                          .fieldName(ServiceWorkType_.NAME)
                                                                          .rejectedValue(serviceTypeName)
                                                                          .message(ErrorMessages.NAME_NOT_UNIQUE)
                                                                          .build());
        }
    }
    
    @Override
    public ServiceWorkTypeDto saveServiceWorkType(ServiceWorkTypeRequestDto serviceWorkTypeRequestDto) {
        validateServiceTypeUniqueness(null, serviceWorkTypeRequestDto.getName());
        ServiceWorkType serviceWorkType = serviceWorkTypeMapper.toEntity(serviceWorkTypeRequestDto);
        return serviceWorkTypeMapper.toDto(serviceWorkTypeRepository.save(serviceWorkType), serviceWorkRepository);
    }

    @Override
    public ServiceWorkTypeDto updateServiceWorkType(long serviceWorkTypeId,
                                                    ServiceWorkTypeRequestDto serviceWorkTypeRequestDto) {
        validateServiceTypeUniqueness(serviceWorkTypeId, serviceWorkTypeRequestDto.getName());
        ServiceWorkType serviceWorkType = serviceWorkTypeRepository.findById(serviceWorkTypeId)
                                                                   .orElseThrow(RecordNotFoundException::new);
        serviceWorkType = serviceWorkTypeMapper.toEntity(serviceWorkTypeRequestDto, serviceWorkType);
        return serviceWorkTypeMapper.toDto(serviceWorkTypeRepository.save(serviceWorkType), serviceWorkRepository);
    }

    @Override
    public Page<ServiceWorkTypeDto> getServiceWorkTypes(ServiceWorkTypeSpecification specification, Pageable pageable) {
        return serviceWorkTypeRepository.findAll(specification, pageable).map(
            serviceWorkType -> serviceWorkTypeMapper.toDto(serviceWorkType, serviceWorkRepository)
        );
    }

    @Override
    public ServiceWorkDto saveServiceWork(long facilityId, ServiceWorkRequestDto serviceWorkRequestDto, User user) {
        Facility facility = accessRightsService.getFacility(facilityId, user);
        ServiceWorkType serviceWorkType = serviceWorkTypeRepository.findById(serviceWorkRequestDto.getServiceWorkType())
                                                                   .orElseThrow(RecordNotFoundException::new);
        ServiceWork serviceWork = serviceWorkMapper.toEntity(serviceWorkRequestDto);
        serviceWork.setFacility(facility);
        serviceWork.setType(serviceWorkType);
        serviceWork = serviceWorkRepository.save(serviceWork);
        applicationEventPublisher.publishEvent(new ServiceWorkPlannedEvent(serviceWork));
        return serviceWorkMapper.toDto(serviceWork);
    }

    @Override
    public ServiceWorkDto updateServiceWork(long serviceWorkId,
                                            ServiceWorkRequestDto serviceWorkRequestDto,
                                            User user) {
        ServiceWork serviceWork = accessRightsService.getServiceWork(serviceWorkId, user);
        ServiceWorkType serviceWorkType = serviceWorkTypeRepository.findById(serviceWorkRequestDto.getServiceWorkType())
                                                                   .orElseThrow(RecordNotFoundException::new);
        serviceWork = serviceWorkMapper.toEntity(serviceWorkRequestDto, serviceWork);
        serviceWork.setType(serviceWorkType);
        serviceWorkRepository.save(serviceWork);
        applicationEventPublisher.publishEvent(new ServiceWorkChangedEvent(serviceWork));
        return serviceWorkMapper.toDto(serviceWork);
    }

    @Override
    public ServiceWorkDto updateServiceWorkResolution(long serviceWorkId, ServiceWorkResolution resolution, User user) {
        ServiceWork serviceWork = accessRightsService.getServiceWork(serviceWorkId, user);
        if (!resolution.equals(serviceWork.getResolution())) {
            serviceWork.setResolution(resolution);
            serviceWorkRepository.save(serviceWork);
            applicationEventPublisher.publishEvent(new ServiceWorkResolutionEvent(resolution, serviceWork));
        }
        return serviceWorkMapper.toDto(serviceWork);
    }

    @Override
    public ServiceWorkDto getServiceWork(long serviceWorkId) {
        return serviceWorkMapper.toDto(getServiceWorkById(serviceWorkId));
    }

    @Override
    public ServiceWorkDto getServiceWork(long serviceWorkId, User user) {
        return serviceWorkMapper.toDto(accessRightsService.getServiceWork(serviceWorkId, user));
    }

    @Override
    public void deleteServiceWork(long serviceWorkId, User user) {
        deleteServiceWork(accessRightsService.getServiceWork(serviceWorkId, user));
    }

    @Override
    public void deleteServiceWork(ServiceWork serviceWork) {
        log.debug("Удаляется сервисная работа {}...", serviceWork.getId());
        serviceWorkRepository.delete(serviceWork);
        applicationEventPublisher.publishEvent(new ServiceWorkDeletedEvent(serviceWork));
        log.debug("Сервисная работа {} удалена.", serviceWork.getId());
    }

    @Override
    public void deleteFacilityServiceWorks(Facility facility) {
        PagedRepositoryReader<ServiceWork> reader = new PagedRepositoryReader<>(
            pageable -> serviceWorkRepository.findAllByFacility(facility, pageable), true);
        int deleted = reader.read(serviceWorks -> {
            log.debug("Удаляются сервисные работы объекта {} ({} из {})...",
                      facility.getId(),
                      serviceWorks.getNumberOfElements(),
                      serviceWorks.getTotalElements());
            serviceWorks.forEach(this::deleteServiceWork);
            return false;
        });
        log.debug("Сервисные работы объекта {} удалены ({}).", facility.getId(), deleted);
    }

    @Override
    public Page<ServiceWorkDto> getServiceWorks(User user, ServiceWorkSpecification specification, Pageable pageable) {
        Page<ServiceWork> page = serviceWorkRepository.findAll(specification, pageable);
        return page.map(serviceWorkMapper::toDto);
    }

    @Override
    public Page<ServiceWorkDto> getServiceWorks(long facilityId, User user, ServiceWorkSpecification specification, Pageable pageable) {
        Facility facility = accessRightsService.getFacility(facilityId, user);
        specification.setFacility(facility);
        return serviceWorkRepository.findAll(specification, pageable)
                                    .map(serviceWorkMapper::toDto);
    }

    private ServiceWork getServiceWorkById(long serviceWorkId) {
        return serviceWorkRepository.findById(serviceWorkId)
                                    .orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public void deleteServiceWorkType(long serviceWorkTypeId, User user) {
        deleteServiceWorkType(accessRightsService.getServiceWorkType(serviceWorkTypeId, user));
    }

    @Override
    public void deleteServiceWorkType(ServiceWorkType serviceWorkType) {
        log.debug("Удаляется тип сервисной работы {}...", serviceWorkType.getId());
        if (serviceWorkRepository.existsByTypeId(serviceWorkType.getId())) {
            log.debug("Невозможно удалить тип `{}`. Привязан к сервисной работе", serviceWorkType.getName());
            throw new CommonValidationException(ValidationErrorResponseDto.builder()
                .formName(ServiceWorkTypeDto.class.getSimpleName())
                .fieldName(ServiceWorkType_.ID)
                .message(ErrorMessages.RECORD_IN_USE)
                .build());
        }
        serviceWorkTypeRepository.delete(serviceWorkType);
        log.debug("Тип сервисной работы {} удален.", serviceWorkType.getId());
    }

    @Override
    public void startServiceWork(long serviceWorkId) {
        ServiceWork serviceWork = getServiceWorkById(serviceWorkId);
        if (!ServiceWorkStatus.PENDING.equals(serviceWork.getStatus())) {
            throw new IllegalStateException("Попытка повторно запустить сервисную работу");
        }
        serviceWork.setStatus(ServiceWorkStatus.IN_PROGRESS);
        serviceWorkRepository.save(serviceWork);
        applicationEventPublisher.publishEvent(new ServiceWorkStartedEvent(serviceWork));
    }

    @Override
    public void finishServiceWork(long serviceWorkId) {
        ServiceWork serviceWork = getServiceWorkById(serviceWorkId);
        if (!ServiceWorkStatus.IN_PROGRESS.equals(serviceWork.getStatus())) {
            throw new IllegalStateException("Попытка остановить незапущенную сервисную работу");
        }
        serviceWork.setStatus(ServiceWorkStatus.FINISHED);
        serviceWorkRepository.save(serviceWork);
        applicationEventPublisher.publishEvent(new ServiceWorkFinishedEvent(serviceWork));
    }
}
