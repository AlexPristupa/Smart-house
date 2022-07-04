package ru.prooftechit.smh.controller.v1.facility;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftechit.smh.api.dto.HardwareDto;
import ru.prooftechit.smh.api.dto.HardwareRequestDto;
import ru.prooftechit.smh.api.dto.RequestFiles;
import ru.prooftechit.smh.api.service.HardwareService;
import ru.prooftechit.smh.api.v1.facility.FacilityHardwareApi;
import ru.prooftechit.smh.controller.v1.AbstractController;
import ru.prooftechit.smh.domain.search.HardwareSpecification;

/**
 * @author Roman Zdoronok
 */
@RestController
@RequiredArgsConstructor
public class FacilityHardwareController extends AbstractController implements FacilityHardwareApi {

    private final HardwareService hardwareService;

    @Override
    public ResponseEntity<HardwareDto> createHardware(Long facilityId, HardwareRequestDto hardware,
                                                      RequestFiles requestFiles) {
        return ResponseEntity.ok(hardwareService.saveHardware(facilityId,
                                                              hardware,
                                                              requestFiles.getPhoto(),
                                                              requestFiles.getImages(),
                                                              getCurrentUser()));
    }

    @Override
    public Page<HardwareDto> getHardware(Long facilityId, String search, Pageable pageable) {
        HardwareSpecification hardwareSpecification = new HardwareSpecification();
        hardwareSpecification.setSearch(search);
        return hardwareService.getHardware(facilityId, getCurrentUser(), hardwareSpecification, pageable);
    }
}
