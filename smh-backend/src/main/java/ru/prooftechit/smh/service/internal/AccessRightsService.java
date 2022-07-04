package ru.prooftechit.smh.service.internal;

import ru.prooftechit.smh.domain.model.*;

/**
 * @author Roman Zdoronok
 */
public interface AccessRightsService {
    Facility getFacility(long facilityId, User user);
    FileSystemNode getFileSystemNode(long nodeId, User user);
    Hardware getHardware(long hardwareId, User user);
    ServiceWork getServiceWork(long serviceWorkId, User user);
    ServiceWorkType getServiceWorkType(long serviceWorkTypeId, User user);
    User getUser(long userId, User user);
}
