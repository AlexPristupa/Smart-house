package ru.prooftechit.smh.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author Roman Zdoronok
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiPaths {
    public static final String ROOT = "/api/v1";

    public static final String AUTH = ROOT + "/auth";
    public static final String AUTH_LOGIN_PART = "/login";
    public static final String AUTH_LOGIN = AUTH + AUTH_LOGIN_PART;
    public static final String AUTH_REFRESH_PART = "/refresh";
    public static final String AUTH_REFRESH = AUTH + AUTH_REFRESH_PART;

    public static final String REGISTRATION = ROOT + "/registration";

    public static final String CONTENT = ROOT + "/content";
    public static final String CONTENT_ID_PART = "/{contentId}";
    public static final String CONTENT_PREVIEW_PART = "/preview";
    public static final String CONTENT_ID_PREVIEW_PART = CONTENT_ID_PART + CONTENT_PREVIEW_PART;

    public static final String DOCUMENTS_PART = "/documents";
    public static final String DOCUMENTS = ROOT + DOCUMENTS_PART;
    public static final String DOCUMENTS_NODE_PART = "/{nodeId}";
    public static final String DOCUMENTS_NODE_LIST_PART = DOCUMENTS_NODE_PART + "/list";

    public static final String HARDWARE_PART = "/hardware";
    public static final String HARDWARE = ROOT + HARDWARE_PART;
    public static final String HARDWARE_ID_PART = "/{hardwareId}";

    public static final String SERVICES_PART = "/service-works";
    public static final String SERVICES = ROOT + SERVICES_PART;
    public static final String SERVICES_ID_PART = "/{serviceWorkId}";
    public static final String SERVICES_RESOLUTION_PART = "/resolution/{resolution}";
    public static final String SERVICES_ID_RESOLUTION_PART = SERVICES_ID_PART + SERVICES_RESOLUTION_PART;
    public static final String SERVICES_TYPES_PART = "/types";
    public static final String SERVICES_TYPES = SERVICES + SERVICES_TYPES_PART;
    public static final String SERVICES_TYPES_ID_PART = SERVICES_TYPES_PART + "/{serviceWorkTypeId}";

    public static final String FACILITIES = ROOT + "/facilities";
    public static final String FACILITIES_ID_PART = "/{facilityId}";

    public static final String FACILITY_DOCUMENTS = FACILITIES + FACILITIES_ID_PART + DOCUMENTS_PART;
    public static final String FACILITY_HARDWARE = FACILITIES + FACILITIES_ID_PART + HARDWARE_PART;
    public static final String FACILITY_SERVICES = FACILITIES + FACILITIES_ID_PART + SERVICES_PART;

    public static final String PROFILE = ROOT + "/profile";
    public static final String PROFILE_CHANGE_PASSWORD_PART = "/change-password";

    public static final String USERS_PART = "/users";
    public static final String USERS = ROOT + USERS_PART;
    public static final String USERS_ID_PART = "/{userId}";
    public static final String USERS_ROLE_PART = "/role/{role}";
    public static final String USERS_ID_ROLE_PART = USERS_ID_PART + USERS_ROLE_PART;

    public static final String USER_EVENT_PART = "/user-events";
    public static final String USER_EVENT = ROOT + USER_EVENT_PART;
    public static final String USER_EVENT_ID_PART = "/{userEventId}";
    public static final String USER_EVENT_READ_PART = "/read";
    public static final String USER_EVENT_READ_ID_PART = USER_EVENT_ID_PART + USER_EVENT_READ_PART;

}
