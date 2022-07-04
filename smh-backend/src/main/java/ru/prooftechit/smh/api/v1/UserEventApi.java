package ru.prooftechit.smh.api.v1;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.prooftechit.smh.api.dto.UserEventDto;
import ru.prooftechit.smh.configuration.swagger.Tags;
import ru.prooftechit.smh.constants.ApiPaths;
import ru.prooftechit.smh.domain.model.UserEvent_;

@Tag(name = Tags.USER_EVENT, description = "API работы с важными событиями пользователей")
@RequestMapping(ApiPaths.USER_EVENT)
public interface UserEventApi {

    @Operation(summary = "Получить список важных событий", tags = Tags.USER_EVENT)
    @GetMapping
    Page<UserEventDto> getUserEvents(
        @RequestParam(required = false) Boolean unread,
        @PageableDefault(sort = UserEvent_.CREATED_AT, direction = Direction.ASC, size = 20) Pageable pageable);

    @Operation(summary = "Отметить прочитанным важное событие", tags = Tags.USER_EVENT)
    @PatchMapping(path = ApiPaths.USER_EVENT_READ_ID_PART)
    ResponseEntity<?> readUserEvent(@PathVariable Long userEventId);

}
