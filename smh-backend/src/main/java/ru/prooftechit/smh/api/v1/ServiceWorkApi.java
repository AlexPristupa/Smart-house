package ru.prooftechit.smh.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.data.web.SortDefault.SortDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prooftechit.smh.api.dto.ServiceWorkDto;
import ru.prooftechit.smh.api.dto.ServiceWorkRequestDto;
import ru.prooftechit.smh.api.dto.ServiceWorkTypeDto;
import ru.prooftechit.smh.api.dto.ServiceWorkTypeRequestDto;
import ru.prooftechit.smh.api.enums.ServiceWorkResolution;
import ru.prooftechit.smh.api.enums.ServiceWorkStatus;
import ru.prooftechit.smh.configuration.swagger.Tags;
import ru.prooftechit.smh.constants.ApiPaths;
import ru.prooftechit.smh.domain.model.ServiceWorkType;
import ru.prooftechit.smh.domain.model.ServiceWorkType_;
import ru.prooftechit.smh.domain.model.ServiceWork_;

import java.util.Set;

/**
 * @author Roman Zdoronok
 */
@Tag(name = Tags.SERVICE, description = "API работы с сервисными работами")
@RequestMapping(ApiPaths.SERVICES)
public interface ServiceWorkApi {

    @Operation(summary = "Получить сервисную работу", tags = Tags.SERVICE)
    @GetMapping(ApiPaths.SERVICES_ID_PART)
    ResponseEntity<ServiceWorkDto> getServiceWork(
        @Parameter(description = "Идентификатор сервисной работы", required = true, example = "1")
        @PathVariable Long serviceWorkId);

    @Operation(summary = "Удалить сервисную работу", tags = Tags.SERVICE)
    @DeleteMapping(ApiPaths.SERVICES_ID_PART)
    ResponseEntity<?> deleteServiceWork(
        @Parameter(description = "Идентификатор сервисной работы", required = true, example = "1")
        @PathVariable Long serviceWorkId);

    @Operation(summary = "Обновить сервисную работу", tags = Tags.SERVICE)
    @PostMapping(path = ApiPaths.SERVICES_ID_PART)
    ResponseEntity<ServiceWorkDto> updateServiceWork(
        @Parameter(description = "Идентификатор сервисной работы", required = true, example = "1")
        @PathVariable Long serviceWorkId,
        @RequestBody @Valid ServiceWorkRequestDto serviceWork);

    @Operation(summary = "Обновить резолюцию сервисной работы", tags = Tags.SERVICE)
    @PatchMapping(path = ApiPaths.SERVICES_ID_RESOLUTION_PART)
    ResponseEntity<ServiceWorkDto> updateServiceWorkResolution(
        @Parameter(description = "Идентификатор сервисной работы", required = true, example = "1")
        @PathVariable Long serviceWorkId,
        @PathVariable ServiceWorkResolution resolution);

    @Operation(summary = "Список всех сервисных работ", tags = Tags.SERVICE)
    @GetMapping
    Page<ServiceWorkDto> getServiceWorks(
        @Parameter (description = "Поисковый запрос", required = false, example = "Проверка")
        @RequestParam(required = false) String search,
        @Parameter (description = "Статусы", required = false, example = "in_progress")
        @RequestParam(required = false) Set<ServiceWorkStatus> statuses,
        @Parameter (description = "Резолюция", required = false, example = "resolved")
        @RequestParam(required = false) ServiceWorkResolution resolution,
        @Parameter (description = "Тип сервисной работы", required = false, example = "Мастер")
        @RequestParam(required = false) ServiceWorkType type,
        @PageableDefault(sort = ServiceWork_.CREATED_AT, direction = Sort.Direction.DESC, size = 20) Pageable pageable);

    @Operation(summary = "Добавить тип сервисной работы", tags = Tags.SERVICE)
    @PostMapping(ApiPaths.SERVICES_TYPES_PART)
    ResponseEntity<ServiceWorkTypeDto> createServiceWorkType(
        @RequestBody @Valid ServiceWorkTypeRequestDto serviceWorkType);

    @Operation(summary = "Обновить тип сервисной работы", tags = Tags.SERVICE)
    @PostMapping(ApiPaths.SERVICES_TYPES_ID_PART)
    ResponseEntity<ServiceWorkTypeDto> updateServiceWorkType(
        @Parameter(description = "Идентификатор типа сервисной работы", required = true, example = "1")
        @PathVariable Long serviceWorkTypeId,
        @RequestBody @Valid ServiceWorkTypeRequestDto serviceWorkType);

    @Operation(summary = "Список всех типов сервисных работ", tags = Tags.SERVICE)
    @GetMapping(ApiPaths.SERVICES_TYPES_PART)
    Page<ServiceWorkTypeDto> getServiceWorkTypes(
        @Parameter (description = "Поисковый запрос", required = false, example = "Проверка")
        @RequestParam(required = false) String search,
        @PageableDefault(size = 20)
        @SortDefaults({@SortDefault(sort = ServiceWorkType_.PINNED, direction = Sort.Direction.DESC),
                       @SortDefault(sort = ServiceWorkType_.CREATED_AT, direction = Sort.Direction.DESC)}) Pageable pageable);

    @Operation(summary = "Удаление типа сервисов", tags = Tags.SERVICE)
    @DeleteMapping(ApiPaths.SERVICES_TYPES_ID_PART)
    ResponseEntity<?> deleteServiceWorkType(
        @Parameter(description = "Идентификатор типа сервисной работы", required = true, example = "1")
        @PathVariable Long serviceWorkTypeId);
}
