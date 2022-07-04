package ru.prooftechit.smh.api.dto.notification;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ru.prooftechit.smh.api.dto.FacilityDto;
import ru.prooftechit.smh.api.dto.ServiceWorkDto;

@RequiredArgsConstructor
@Getter
public class ServiceWorkNotificationDto {
    private final ServiceWorkDto serviceWorkDto;
    private final FacilityDto facilityDto;
}
