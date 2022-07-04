package ru.prooftechit.smh.api.v1.facility;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prooftechit.smh.api.dto.HardwareDto;
import ru.prooftechit.smh.api.dto.HardwareRequestDto;
import ru.prooftechit.smh.api.dto.RequestFiles;
import ru.prooftechit.smh.configuration.swagger.Tags;
import ru.prooftechit.smh.constants.ApiPaths;
import ru.prooftechit.smh.domain.model.Hardware_;

/**
 * @author Roman Zdoronok
 */
@Tag(name = Tags.HARDWARE, description = "API работы с оборудованием")
@RequestMapping(ApiPaths.FACILITY_HARDWARE)
public interface FacilityHardwareApi {

    @Operation(summary = "Добавить устройство объекту", tags = {Tags.HARDWARE, Tags.FACILITY})
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<HardwareDto> createHardware(
        @Parameter(description = "Идентификатор объекта", required = true, example = "1") @PathVariable Long facilityId,
        @RequestPart @Valid HardwareRequestDto hardware,
        @Valid RequestFiles requestFiles);

    @Operation(summary = "Список всех устройств объекта", tags = {Tags.HARDWARE, Tags.FACILITY})
    @GetMapping
    Page<HardwareDto> getHardware(
        @Parameter(description = "Идентификатор объекта", required = true, example = "1") @PathVariable Long facilityId,
        @Parameter (description = "Поисковый запрос", required = false, example = "Проверка")
        @RequestParam(required = false) String search,
        @PageableDefault(sort = Hardware_.CREATED_AT, direction = Sort.Direction.DESC, size = 20) Pageable pageable);

}
