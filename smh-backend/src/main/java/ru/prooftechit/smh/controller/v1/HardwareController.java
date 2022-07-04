package ru.prooftechit.smh.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.prooftechit.smh.api.dto.HardwareDto;
import ru.prooftechit.smh.api.dto.HardwareRequestDto;
import ru.prooftechit.smh.api.dto.RequestFiles;
import ru.prooftechit.smh.api.service.HardwareService;
import ru.prooftechit.smh.api.v1.HardwareApi;
import ru.prooftechit.smh.domain.search.HardwareSpecification;

/**
 * @author Roman Zdoronok
 */
@RestController
@RequiredArgsConstructor
public class HardwareController extends AbstractController implements HardwareApi {

    private final HardwareService hardwareService;

    @Override
    public ResponseEntity<HardwareDto> getHardware(Long hardwareId) {
        return ResponseEntity.ok(hardwareService.getHardware(hardwareId, getCurrentUser()));
    }

    @Override
    public ResponseEntity<?> deleteHardware(Long hardwareId) {
        hardwareService.deleteHardware(hardwareId, getCurrentUser());
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<HardwareDto> updateHardware(Long hardwareId, HardwareRequestDto hardware,
                                                      RequestFiles requestFiles) {
        return ResponseEntity.ok(hardwareService.updateHardware(hardwareId,
                                                                hardware,
                                                                requestFiles.getPhoto(),
                                                                requestFiles.getImages(),
                                                                getCurrentUser()));
    }

    @Override
    public Page<HardwareDto> getHardware(String search,
                                         Pageable pageable) {
        HardwareSpecification hardwareSpecification = new HardwareSpecification();
        hardwareSpecification.setSearch(search);
        return hardwareService.getHardware(getCurrentUser(), hardwareSpecification, pageable);
    }
}
