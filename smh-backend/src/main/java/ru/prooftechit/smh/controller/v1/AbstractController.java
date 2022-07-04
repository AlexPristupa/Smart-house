package ru.prooftechit.smh.controller.v1;

import ru.prooftechit.smh.configuration.security.SecurityContextHolderWrapper;
import ru.prooftechit.smh.domain.model.User;

/**
 * @author Roman Zdoronok
 */
public abstract class AbstractController {

    protected User getCurrentUser() {
        return SecurityContextHolderWrapper.getCurrentUser();
    }
}
