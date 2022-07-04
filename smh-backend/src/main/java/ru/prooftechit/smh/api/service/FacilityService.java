package ru.prooftechit.smh.api.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.api.dto.FacilityDto;
import ru.prooftechit.smh.api.dto.FacilityRequestDto;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.search.FacilitySpecification;

/**
 * @author Roman Zdoronok
 */
public interface FacilityService {
    FacilityDto saveFacility(FacilityRequestDto facilityRequestDto, MultipartFile photo, List<MultipartFile> images, User user);
    FacilityDto updateFacility(long facilityId, FacilityRequestDto facilityRequestDto, MultipartFile photo, List<MultipartFile> images, User user);
    FacilityDto getFacility(long facilityId, User user);
    void deleteFacility(long facilityId, User user);
    Page<FacilityDto> getFacilities(User user, FacilitySpecification specification, Pageable pageable);
}
