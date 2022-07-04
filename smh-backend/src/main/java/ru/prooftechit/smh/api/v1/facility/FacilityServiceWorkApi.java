package ru.prooftechit.smh.api.v1.facility;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prooftechit.smh.api.dto.ServiceWorkDto;
import ru.prooftechit.smh.api.dto.ServiceWorkRequestDto;
import ru.prooftechit.smh.api.enums.ServiceWorkResolution;
import ru.prooftechit.smh.api.enums.ServiceWorkStatus;
import ru.prooftechit.smh.configuration.swagger.Tags;
import ru.prooftechit.smh.constants.ApiPaths;
import ru.prooftechit.smh.domain.model.ServiceWorkType;
import ru.prooftechit.smh.domain.model.ServiceWork_;

import java.util.Set;

/**
 * @author Roman Zdoronok
 */
@Tag(name = Tags.SERVICE, description = "API работы с сервисными работами")
@RequestMapping(ApiPaths.FACILITY_SERVICES)
public interface FacilityServiceWorkApi {

    @Operation(summary = "Добавить сервисную работу объекту", tags = {Tags.SERVICE, Tags.FACILITY})
    @PostMapping
    ResponseEntity<ServiceWorkDto> createServiceWork(
        @Parameter(description = "Идентификатор объекта", required = true, example = "1") @PathVariable Long facilityId,
        @RequestBody @Valid ServiceWorkRequestDto serviceWork);

    @Operation(summary = "Список всех сервисных работ объекта", tags = {Tags.SERVICE, Tags.FACILITY})
    @GetMapping
    Page<ServiceWorkDto> getServiceWorks(
        @Parameter(description = "Идентификатор объекта", required = true, example = "1") @PathVariable Long facilityId,
        @Parameter (description = "Поисковый запрос", required = false, example = "Проверка")
        @RequestParam(required = false) String search,
        @Parameter (description = "Статусы", required = false, example = "in_progress")
        @RequestParam(required = false) Set<ServiceWorkStatus> statuses,
        @Parameter (description = "Резолюция", required = false, example = "resolved")
        @RequestParam(required = false) ServiceWorkResolution resolution,
        @Parameter (description = "Тип сервисной работы", required = false, example = "Мастер")
        @RequestParam(required = false) ServiceWorkType type,
        @PageableDefault(sort = ServiceWork_.CREATED_AT, direction = Sort.Direction.DESC, size = 20) Pageable pageable);

}
