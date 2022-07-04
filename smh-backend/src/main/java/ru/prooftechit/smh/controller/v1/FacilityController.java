package ru.prooftechit.smh.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftechit.smh.api.dto.FacilityDto;
import ru.prooftechit.smh.api.dto.FacilityRequestDto;
import ru.prooftechit.smh.api.dto.RequestFiles;
import ru.prooftechit.smh.api.service.FacilityService;
import ru.prooftechit.smh.api.v1.FacilityApi;
import ru.prooftechit.smh.domain.search.FacilitySpecification;

/**
 * @author Roman Zdoronok
 */
@RestController
@RequiredArgsConstructor
public class FacilityController extends AbstractController implements FacilityApi {

    private final FacilityService facilityService;

    @Override
    public ResponseEntity<FacilityDto> createFacility(FacilityRequestDto facility,
                                                      RequestFiles requestFiles) {
        return ResponseEntity.ok(facilityService.saveFacility(facility,
                                                              requestFiles.getPhoto(),
                                                              requestFiles.getImages(),
                                                              getCurrentUser()));
    }

    @Override
    public ResponseEntity<FacilityDto> updateFacility(Long facilityId,
                                                      FacilityRequestDto facility,
                                                      RequestFiles requestFiles) {
        return ResponseEntity.ok(facilityService.updateFacility(facilityId,
                                                                facility,
                                                                requestFiles.getPhoto(),
                                                                requestFiles.getImages(),
                                                                getCurrentUser()));
    }

    @Override
    public ResponseEntity<FacilityDto> getFacility(Long facilityId) {
        return ResponseEntity.ok(facilityService.getFacility(facilityId, getCurrentUser()));
    }

    @Override
    public ResponseEntity<?> deleteFacility(Long facilityId) {
        facilityService.deleteFacility(facilityId, getCurrentUser());
        return ResponseEntity.noContent().build();
    }

    @Override
    public Page<FacilityDto> getFacilities(String search,
                                           Pageable pageable) {
        FacilitySpecification facilitySpecification = new FacilitySpecification();
        facilitySpecification.setSearch(search);
        return facilityService.getFacilities(getCurrentUser(), facilitySpecification, pageable);
    }
}
