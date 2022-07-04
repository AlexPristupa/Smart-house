package ru.prooftechit.smh.api.v1;

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
@RequestMapping(ApiPaths.HARDWARE)
public interface HardwareApi {

    @Operation(summary = "Получить устройство", tags = Tags.HARDWARE)
    @GetMapping(ApiPaths.HARDWARE_ID_PART)
    ResponseEntity<HardwareDto> getHardware(
        @Parameter(description = "Идентификатор устройства", required = true, example = "1") @PathVariable Long hardwareId);

    @Operation(summary = "Удалить устройство", tags = Tags.HARDWARE)
    @DeleteMapping(ApiPaths.HARDWARE_ID_PART)
    ResponseEntity<?> deleteHardware(
        @Parameter(description = "Идентификатор устройства", required = true, example = "1") @PathVariable Long hardwareId);

    @Operation(summary = "Обновить устройство", tags = Tags.HARDWARE)
    @PostMapping(path = ApiPaths.HARDWARE_ID_PART, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<HardwareDto> updateHardware(
        @Parameter(description = "Идентификатор устройства", required = true, example = "1") @PathVariable Long hardwareId,
        @RequestPart @Valid HardwareRequestDto hardware,
        @Valid RequestFiles requestFiles);

    @Operation(summary = "Список всех устройств", tags = Tags.HARDWARE)
    @GetMapping
    Page<HardwareDto> getHardware(
        @Parameter (description = "Поисковый запрос", required = false, example = "Счетчик")
        @RequestParam(required = false) String search,
        @PageableDefault(sort = Hardware_.CREATED_AT, direction = Sort.Direction.DESC, size = 20) Pageable pageable);
}
