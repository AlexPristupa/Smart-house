package ru.prooftechit.smh.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Duration;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Roman Zdoronok
 */
@Schema(description = "Информация об отрезке времени")
@Getter
@RequiredArgsConstructor
public class DurationDto {

    @Schema(description = "Дни")
    private final long days;

    @Schema(description = "Часы дня")
    private final int hours;

    @Schema(description = "Минуты часа")
    private final int minutes;

    @Schema(description = "Секунды минуты")
    private final int seconds;


    public static DurationDto of(Duration duration) {

        return new DurationDto(duration.toDays(),
                               duration.toHoursPart(),
                               duration.toMinutesPart(),
                               duration.toSecondsPart());
    }

    public static DurationDto of(int days, int hours, int minutes, int seconds) {
        return new DurationDto(days, hours, minutes, seconds);
    }

    public static DurationDto ofDays(int days) {
        return new DurationDto(days, 0, 0, 0);
    }

    public static DurationDto ofHours(int hours) {
        return new DurationDto(0, hours, 0, 0);
    }

    public static DurationDto ofMinutes(int minutes) {
        return new DurationDto(0, 0, minutes, 0);
    }
    public static DurationDto ofSeconds(int seconds) {
        return new DurationDto(0, 0, 0, seconds);
    }
}
