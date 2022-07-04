package ru.prooftechit.smh.api.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import ru.prooftechit.smh.api.dto.HardwareDto;
import ru.prooftechit.smh.api.dto.HardwareRequestDto;
import ru.prooftechit.smh.domain.model.User;
import ru.prooftechit.smh.domain.search.HardwareSpecification;

/**
 * @author Roman Zdoronok
 */
public interface HardwareService {
    HardwareDto saveHardware(long facilityId, HardwareRequestDto hardwareRequestDto, MultipartFile photo, List<MultipartFile> images, User user);
    HardwareDto updateHardware(long hardwareId, HardwareRequestDto hardwareRequestDto, MultipartFile photo, List<MultipartFile> images, User user);
    HardwareDto getHardware(long hardwareId, User user);
    void deleteHardware(long hardwareId, User user);
    Page<HardwareDto> getHardware(User user, HardwareSpecification specification, Pageable pageable);
    Page<HardwareDto> getHardware(long facilityId, User user, HardwareSpecification specification, Pageable pageable);
}
