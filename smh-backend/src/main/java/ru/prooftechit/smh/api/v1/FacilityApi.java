package ru.prooftechit.smh.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Encoding;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prooftechit.smh.api.dto.FacilityDto;
import ru.prooftechit.smh.api.dto.FacilityRequestDto;
import ru.prooftechit.smh.api.dto.RequestFiles;
import ru.prooftechit.smh.configuration.swagger.Tags;
import ru.prooftechit.smh.constants.ApiPaths;
import ru.prooftechit.smh.domain.model.Facility_;

/**
 * @author Roman Zdoronok
 */
@Tag(name = Tags.FACILITY, description = "API для работы с объектами")
@RequestMapping(ApiPaths.FACILITIES)
public interface FacilityApi {

    @Operation(summary = "Создать объект", tags = Tags.FACILITY)
    @RequestBody(content = @Content(encoding = @Encoding(name = "facility", contentType = MediaType.APPLICATION_JSON_VALUE)))
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FacilityDto> createFacility(
        @RequestPart @Valid FacilityRequestDto facility,
        @Valid RequestFiles requestFiles);

    @Operation(summary = "Обновить объект", tags = Tags.FACILITY)
    @RequestBody(content = @Content(encoding = @Encoding(name = "facility", contentType = MediaType.APPLICATION_JSON_VALUE)))
    @PostMapping(path = ApiPaths.FACILITIES_ID_PART, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<FacilityDto> updateFacility(
        @Parameter(description = "Идентификатор объекта", required = true, example = "1") @PathVariable Long facilityId,
        @RequestPart @Valid FacilityRequestDto facility,
        @Valid RequestFiles requestFiles);

    @Operation(summary = "Получить объект", tags = Tags.FACILITY)
    @GetMapping(ApiPaths.FACILITIES_ID_PART)
    ResponseEntity<FacilityDto> getFacility(
        @Parameter(description = "Идентификатор объекта", required = true, example = "1") @PathVariable Long facilityId);

    @Operation(summary = "Удалить объект", tags = Tags.FACILITY)
    @DeleteMapping(ApiPaths.FACILITIES_ID_PART)
    ResponseEntity<?> deleteFacility(
        @Parameter(description = "Идентификатор объекта", required = true, example = "1") @PathVariable Long facilityId);

    @Operation(summary = "Список всех объектов", tags = Tags.FACILITY)
    @GetMapping
    Page<FacilityDto> getFacilities(
        @Parameter (description = "Поисковый запрос", required = false, example = "Ленина")
        @RequestParam(required = false) String search,
        @PageableDefault(sort = Facility_.CREATED_AT, direction = Sort.Direction.DESC, size = 20) Pageable pageable);

}
