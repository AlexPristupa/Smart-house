package ru.prooftechit.smh.mapper;

import org.mapstruct.Mapper;

import java.util.Date;
import java.util.UUID;

/**
 * @author Roman Zdoronok
 */
@Mapper(componentModel = "spring")
public interface CommonMapper {

    default UUID uuidFromString(String val) {
        return UUID.fromString(val);
    }

    default String uuidToString(UUID uuid) {
        return uuid.toString();
    }

//    default String fileToString(File file) {
//        return uuidToString(file.getContentId());
//    }
//
//    default UUID fileToUuid(File file) {
//        return file.getContentId();
//    }

    default Long timestampFromDate(Date date) {
        return date == null ? null : date.getTime();
    }

    default Date dateFromTimestamp(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

}
